package cz.simkovicp.hazelcastviewer.bean;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class JsonValue {

    private final Class<?> clazz;

    @JsonRawValue
    public final String json;

    public final String toString;

    public JsonValue(Class<?> clazz, String json, String toString) {
        this.clazz = clazz;
        this.json = json;
        this.toString = toString;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getJson() {
        return json;
    }

    public String getToString() {
        return toString;
    }

}
