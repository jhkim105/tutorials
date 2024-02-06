package jhkim105.rsupport.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


public class JacksonConfig {

  public final static String DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
  private final static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER =
      new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
  @Configuration
  static class RegisterModuleConfig {
    @Bean
    public JavaTimeModule javaTimeModule() {
      JavaTimeModule module = new JavaTimeModule();
      module.addSerializer(LOCAL_DATETIME_SERIALIZER);
      return module;
    }
  }

//  @Configuration
  static class BuilderCustomizerConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
      return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL)
          .serializers(LOCAL_DATETIME_SERIALIZER);
    }
  }
//  @Configuration
  static class ObjectMapperConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
      JavaTimeModule module = new JavaTimeModule();
      module.addSerializer(LOCAL_DATETIME_SERIALIZER);
      return new ObjectMapper()
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .setSerializationInclusion(JsonInclude.Include.NON_NULL)
          .registerModule(module);
    }
  }
//  @Configuration
  static class ObjectMapperBuilderConfig {
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
      return new Jackson2ObjectMapperBuilder().serializers(LOCAL_DATETIME_SERIALIZER)
          .serializationInclusion(JsonInclude.Include.NON_NULL);
    }
  }
//  @Configuration
  static class HttpMessageConverterConfig {
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
      Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder().serializers(LOCAL_DATETIME_SERIALIZER)
          .serializationInclusion(JsonInclude.Include.NON_NULL);
      return new MappingJackson2HttpMessageConverter(builder.build());
    }
  }


}
