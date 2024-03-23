package com.ua.money24.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;

@Component
public class TelegramBotCommandInitializer implements InitializingBean {
    private final ICommandRegistry commandBot;
    private final IBotCommand[] botCommands;

    public TelegramBotCommandInitializer(ICommandRegistry commandBot, IBotCommand... botCommands) {
        this.commandBot = commandBot;
        this.botCommands = botCommands;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.commandBot.registerAll(botCommands);
    }
}
