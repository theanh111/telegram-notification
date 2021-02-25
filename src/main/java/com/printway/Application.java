package com.printway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
//@EnableScheduling
public class Application extends SpringBootServletInitializer {

    private static final Class<Application> applicationClass = Application.class;

    public static void main(String[] args) {
        if (System.getProperty("spring.profiles.active") == null) {
            System.setProperty("spring.profiles.active", "dev");
        }
        ApplicationContext ctx = SpringApplication.run(applicationClass, args);
//        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        if (System.getProperty("spring.profiles.active") == null) {
            System.setProperty("spring.profiles.active", "dev");
        }
        logger.info("PROFILE ACTIVE: " + System.getProperty("spring.profiles.active"));
        return application.sources(applicationClass);
    }

//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
}
