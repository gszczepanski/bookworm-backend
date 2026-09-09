package org.bookworm.library;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "swagger")
@Getter
@Setter
public class SwaggerConfig {

    private String title;
    private String version;
    private String description;
    private String contactName;
    private String contactAddress;
    private String contactUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title(title)
                .version(version)
                .description(description)
                .contact(new Contact()
                    .name(contactName)
                    .url(contactUrl)
                    .email(contactAddress)
                    )
                );
    }
}
