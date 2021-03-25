package com.printway;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@EnableAsync
public class Application extends SpringBootServletInitializer {
    private static final Class<Application> applicationClass = Application.class;
    private final Logger logger = Logger.getLogger(this.getClass());

    public static void main(String[] args) {
        if (System.getProperty("spring.profiles.active") == null) {
            System.setProperty("spring.profiles.active", "prod");
        }
        ApiContextInitializer.init();
        ApplicationContext ctx = SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        if (System.getProperty("spring.profiles.active") == null) {
            System.setProperty("spring.profiles.active", "dev");
        }
        logger.info("PROFILE ACTIVE: " + System.getProperty("spring.profiles.active"));
        return application.sources(applicationClass);
    }
}
