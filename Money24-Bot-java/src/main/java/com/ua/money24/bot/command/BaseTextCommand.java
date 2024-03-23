package com.ua.money24.bot.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;

public abstract class BaseTextCommand implements IBotCommand {
    private final String commandIdentifier;
    private final String description;

    protected BaseTextCommand(String command, String description) {
        this.commandIdentifier = command;
        this.description = description;
    }

    @Override
    public String getCommandIdentifier() {
        return commandIdentifier;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
