package com.ua.money24.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;

@Configuration
@EnableConfigurationProperties({
        TelegramBotProperties.class
})
@Import(TelegramBotStarterConfiguration.class)
public class ApplicationConfig {
}