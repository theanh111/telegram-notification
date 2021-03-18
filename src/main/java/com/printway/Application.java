package com.printway;

import com.printway.exception.BaseResponse;
import com.printway.telegrambot.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@EnableAsync
public class Application extends SpringBootServletInitializer {
    @Bean
    public BaseResponse baseResponse() {
        return new BaseResponse();
    }

    private static final Class<Application> applicationClass = Application.class;

    public static void main(String[] args) throws Exception{
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
