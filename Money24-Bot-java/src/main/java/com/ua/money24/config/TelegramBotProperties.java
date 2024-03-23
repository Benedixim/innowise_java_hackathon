package com.ua.money24.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("telegram-bot")
public record TelegramBotProperties(String token, String username) {
}
