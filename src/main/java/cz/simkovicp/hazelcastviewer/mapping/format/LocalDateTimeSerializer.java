package cz.simkovicp.hazelcastviewer.mapping.format;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Serializer to write LocalDateTime into JSON with system time zone.
 * 
 * @author simkovicp
 *
 */
public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
    
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 102105526844883171L;
    /**
     * Formatter
     */
    private final transient DateTimeFormatter formatter;
    
    /**
     * Constructs the serializer for LocalDateTime.
     * 
     * @param formatter the datetime formatter
     */
    public LocalDateTimeSerializer(DateTimeFormatter formatter) {
        super(LocalDateTime.class);
        this.formatter = formatter;
    }

    /**
     * Serializes the local datetime into JSON string with time zone information.
     * 
     * @param value    the datetime to serialize
     * @param gen      the generator to write JSON content
     * @param provider the serializer provider
     */
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        OffsetDateTime zdt = value.atZone(ZoneId.systemDefault()).toOffsetDateTime();
        gen.writeString(zdt.format(formatter));
    }

}
