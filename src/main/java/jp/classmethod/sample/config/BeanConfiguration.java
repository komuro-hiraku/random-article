package jp.classmethod.sample.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

import java.time.Duration;

@Configuration
public class BeanConfiguration {

    @Bean
    ObjectMapper objectMapper() {
        var mapper =  new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);    // SnakeCase -> CamelCase
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 未知のプロパティは無視
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true); // Uppercase, Lowercase入り混じったものを受け入れ
        return mapper;
    }

    @Bean
    RestOperations restOperations() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(30))  // 30秒タイムアウト
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }
}
