package cz.simkovicp.hazelcastviewer.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

/**
 * Configuration of HazelcastInstance
 */
@Configuration
@EnableAutoConfiguration(exclude={HazelcastAutoConfiguration.class})
public class HazelcastConfiguration {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${hazelcast.client.instance.name}")
    private String hazelcastClientInstance;

    @Value("${hazelcast.server.addresses}")
    private String hazelcastAddresses;
    
    @Value("${hazelcast.group.name}")
    private String hazelcastGroupName;

    @Value("${hazelcast.group.password}")
    private String hazelcastGroupPasswod;


    @Bean(destroyMethod="shutdown")
    public HazelcastInstance hazelcastInstance() {
        List<String> hazelcastAddressList = hazelcastAddresses != null
                ? Arrays.asList(hazelcastAddresses.split(","))
                : Collections.<String>emptyList();

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setInstanceName(hazelcastClientInstance);
        clientConfig.setGroupConfig(new GroupConfig(hazelcastGroupName, hazelcastGroupPasswod));
        
        ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();
        networkConfig.setAddresses(hazelcastAddressList);
        networkConfig.setSmartRouting(true);
        networkConfig.setRedoOperation(true);

        HazelcastInstance instance = HazelcastClient.newHazelcastClient(clientConfig);
        
        instance.getDistributedObjects().stream().forEach(d -> {
            
            logger.info("distributed object, name: {}, serviceName: {}, partitionKey: {}, {}", 
                    d.getName(), d.getServiceName(), d.getPartitionKey(), d.toString());
            
            if (d instanceof ITopic) {
                instance.getTopic(d.getName()).addMessageListener(
                        m -> {logger.info("topic {} received msg {}, member: {}, content: {}", 
                                d.getName(), m, m.getPublishingMember(), m.getMessageObject());});
            }
        });
        return instance;
    }

}
