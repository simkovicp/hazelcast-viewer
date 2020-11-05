package cz.simkovicp.hazelcastviewer.mapping.format;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Deserializer to parse the JSON string representing date.
 */
public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -7381273553805654851L;
    /**
     * Formatter
     */
    private final transient DateTimeFormatter formatter;

    /**
     * Constructs the deserializer for LocalDate.
     * 
     * @param formatter the datetime formatter
     */
    public LocalDateDeserializer(DateTimeFormatter formatter) {
        super(LocalDate.class);
        this.formatter = formatter;
    }

    /**
     * Parses the JSON string representing date.
     * 
     * @param p    the parsed used for reading JSON content
     * @param ctxt the context that can be used to access information about this
     *             deserialization activity
     *             
     * @return the instance of the LocalDatetTime
     */
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        return LocalDate.parse(p.getText(), formatter);
    }

}
