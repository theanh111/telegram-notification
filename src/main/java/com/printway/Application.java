package com.printway;

import com.printway.telegrambot.PrintwayChatbot;
import com.printway.telegrambot.PrintwayChatbotCountError;
import com.printway.telegrambot.PrintwayChatbotStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.sql.DataSource;

@SpringBootApplication
@AutoConfigurationPackage
@PropertySources({@PropertySource("classpath:application.properties")})
public class Application {
    @Autowired
    private Environment env;

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    public static void main(String[] args) {

        for (String s : args) {
            if (s.equals("tracking")) {
                ApiContextInitializer.init();
                TelegramBotsApi botsApi = new TelegramBotsApi();
                try {
                    PrintwayChatbot bot = new PrintwayChatbot();
                    botsApi.registerBot(bot);
                    bot.runThis();
                    System.out.println("Send Tracking Error To Group Chat!");
                } catch (TelegramApiRequestException exception) {
                    exception.printStackTrace();
                    System.exit(0);
                }
            }
            if (s.equals("auto")) {
                ApiContextInitializer.init();
                TelegramBotsApi botsApi = new TelegramBotsApi();
                try {
                    PrintwayChatbotStatistic bot = new PrintwayChatbotStatistic();
                    botsApi.registerBot(bot);
                    bot.runThis();
                    System.out.println("Send Statistic To Group Chat!");
                } catch (TelegramApiRequestException exception) {
                    exception.printStackTrace();
                    System.exit(0);
                }
            }
            if (s.equals("error")) {
                ApiContextInitializer.init();
                TelegramBotsApi botsApi = new TelegramBotsApi();
                try {
                    PrintwayChatbotCountError bot = new PrintwayChatbotCountError();
                    botsApi.registerBot(bot);
                    bot.runThis();
                    System.out.println("Send Error Orders To Group Chat!");
                } catch (TelegramApiRequestException exception) {
                    exception.printStackTrace();
                    System.exit(0);
                }
            }
        }

        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
            @Override
            public int getExitCode() {
                return 0;
            }
        });
        System.exit(exitCode);
    }
}
