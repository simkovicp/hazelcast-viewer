package cz.simkovicp.hazelcastviewer.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import cz.simkovicp.hazelcastviewer.mapping.format.LocalDateDeserializer;
import cz.simkovicp.hazelcastviewer.mapping.format.LocalDateSerializer;
import cz.simkovicp.hazelcastviewer.mapping.format.LocalDateTimeDeserializer;
import cz.simkovicp.hazelcastviewer.mapping.format.LocalDateTimeSerializer;

@Configuration
public class WebConfig {
    
    /**
     * Date format used in REST client-server communication.
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * Datetime format used in REST client-server communication.
     */
    public static final  String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    
    /**
     * Returns common date formatter. It's is immutable and is thread-safe.
     * 
     * @return the date formatter
     */
    @Bean
    public DateTimeFormatter dateFormatter() {
        return DateTimeFormatter.ofPattern(DATE_PATTERN);
    }

    /**
     * Returns common datetime formatter. It's is immutable and is thread-safe.
     * 
     * @return the datetime formatter
     */
    @Bean
    public DateTimeFormatter datetimeFormatter() {
        return DateTimeFormatter.ofPattern(DATETIME_PATTERN).withZone(ZoneId.systemDefault());
    }

    /**
     * Returns customizer for Jackson ObjectMapper.
     * 
     * @param dateFormatter     the date formatter
     * @param datetimeFormatter the datetime formatter
     * @return the customizer for Jackson ObjectMapper
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer(DateTimeFormatter dateFormatter,
            DateTimeFormatter datetimeFormatter) {

        return b -> {
            b.failOnUnknownProperties(true);
            b.serializerByType(LocalDate.class, new LocalDateSerializer(dateFormatter));
            b.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(datetimeFormatter));
            b.deserializerByType(LocalDate.class, new LocalDateDeserializer(dateFormatter));
            b.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(datetimeFormatter));
        };

    }

    
    @Autowired(required = true)
    public void configureJackson(ObjectMapper jackson2ObjectMapper) {
        // some beans have private fields without any getters
        jackson2ObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
    

}
