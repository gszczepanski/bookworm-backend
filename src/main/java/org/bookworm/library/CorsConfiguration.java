package org.bookworm.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer(Environment env) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(env.getProperty("ws.cross.origin.address"))
                        .allowedMethods("GET, POST, PUT, DELETE, PATCH")
                        .allowedHeaders("*")
                        // .allowedHeaders("Origin", "Accept", "X-Requested-With", "Content-Type",
                        //  "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization")
                        .exposedHeaders("*")
                        .allowCredentials(false).maxAge(3600);
            }
        };
    }


}
