import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Register the JavaTimeModule for Java 8 date/time types
        objectMapper.registerModule(new JavaTimeModule());

        // Disable writing dates as timestamps (e.g., 2023-01-01 instead of epoch time)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Configure the default date format for serialization/deserialization
        objectMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd"));

        return objectMapper;
    }
}
