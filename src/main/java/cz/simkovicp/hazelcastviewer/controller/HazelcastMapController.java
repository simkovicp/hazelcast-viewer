package cz.simkovicp.hazelcastviewer.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.IMap;
import com.hazelcast.map.impl.MapService;

import cz.simkovicp.hazelcastviewer.bean.JsonValue;

/**
 * REST controller for operations on Hazelcast maps
 */
@RestController
@RequestMapping("/maps")
public class HazelcastMapController extends AbstractHazelcastController {
    
    @Autowired
    private ObjectMapper jackson2ObjectMapper;

    /**
     * Get a list of the names of all maps
     * @return list of map names
     */
    @RequestMapping(method = GET)
    public List<String> getMaps() {
        logger.debug("Getting list of maps");
        return getDistributedObjectNames(MapService.SERVICE_NAME);
    }

    /**
     * Get the contents of a map
     * @param mapName the name of the map
     * @return the contents of a map
     */
    @RequestMapping(method = GET, value = "/{mapName}")
    public Map<?, ?> getMap(@PathVariable("mapName") String mapName) {
        logger.debug("Viewing map: {}", mapName);
        return toMap(hazelcastInstance.getMap(mapName));
    }

    /**
     * Get the contents of a specific entry in the map
     * @param mapName the name of the map
     * @param mapKey the key of the map entry
     * @return the contents of a specific entry in the map
     */
    @RequestMapping(method = GET, value = "/{mapName}/{mapKey}")
    public Object getMapObject(@PathVariable("mapName") String mapName,
                               @PathVariable("mapKey") String mapKey) {
        logger.debug("Viewing map object: {}[{}]", mapName, mapKey);
        return hazelcastInstance.getMap(mapName).get(mapKey);
    }

    private <K, V> Map<K, JsonValue> toMap(IMap<K, V> hazelcastMap) {
        Map<K, JsonValue> map = new HashMap<>();
        
        for (K key : hazelcastMap.keySet()) {
            V v = null;
            try {
                v = hazelcastMap.get(key);
                map.put(key, new JsonValue(v.getClass(), jackson2ObjectMapper.writeValueAsString(v), null));
            } catch (Exception ex) {
                logger.warn("toMap() - key: " + key, ex);
                if (v == null) {
                    map.put(key, new JsonValue(ex.getClass(), null, ex.getMessage()));
                } else {
                    map.put(key, new JsonValue(v.getClass(), null, v.toString()));
                }
            }
        }
        return map;
    }
    
}
