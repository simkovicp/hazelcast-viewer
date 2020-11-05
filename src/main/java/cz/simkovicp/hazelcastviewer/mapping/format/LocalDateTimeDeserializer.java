package cz.simkovicp.hazelcastviewer.mapping.format;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Deserializer to parse the JSON string representing date and time with zone
 * and to convert it into date and time in local time zone.
 */
public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -7381273553805654851L;
    /**
     * Formatter
     */
    private final transient DateTimeFormatter formatter;

    /**
     * Constructs the deserializer for LocalDateTime.
     * 
     * @param formatter the datetime formatter
     */
    public LocalDateTimeDeserializer(DateTimeFormatter formatter) {
        super(LocalDateTime.class);
        this.formatter = formatter;
    }

    /**
     * Parses the JSON string representing date and time with zone and converts it
     * into date and time in local time zone.
     * 
     * @param p    the parsed used for reading JSON content
     * @param ctxt the context that can be used to access information about this
     *             deserialization activity
     *             
     * @return the instance of the LocalDatetTime
     */
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        return OffsetDateTime.parse(p.getText(), formatter)
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
