package sergesv.rvs.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageJacksonSerializer extends JsonSerializer<Page> {
    @Override
    public void serialize(Page value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("content", value.getContent());
        gen.writeNumberField("current", value.getNumber());
        gen.writeNumberField("size", value.getSize());
        gen.writeNumberField("total", value.getTotalPages());
        gen.writeEndObject();
    }
}
