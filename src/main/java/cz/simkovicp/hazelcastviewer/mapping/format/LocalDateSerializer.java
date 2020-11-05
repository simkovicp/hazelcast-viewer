package cz.simkovicp.hazelcastviewer.mapping.format;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Serializer to write LocalDate into JSON.
 * 
 * @author simkovicp
 *
 */
public class LocalDateSerializer extends StdSerializer<LocalDate> {
    
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
    public LocalDateSerializer(DateTimeFormatter formatter) {
        super(LocalDate.class);
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
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.format(formatter));
    }

}
