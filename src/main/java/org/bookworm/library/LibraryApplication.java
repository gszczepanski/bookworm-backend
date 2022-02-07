package org.bookworm.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@EnableCaching // integration tests use @Sql to prepare database before each test and Redis is unable to see it
public class LibraryApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LibraryApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

}


