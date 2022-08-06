package app.config;

import app.util.JsonMapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentConfig {

    @Bean
    public JsonMapper jsonMapper() {
        final var objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.AUTO_DETECT_CREATORS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_FIELDS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
        objectMapper.configure(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS, false);
        objectMapper.configure(MapperFeature.INFER_PROPERTY_MUTATORS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        return new JsonMapper(objectMapper);
    }

}
