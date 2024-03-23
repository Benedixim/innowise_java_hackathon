package com.ua.money24.bot.command;

import com.ua.money24.constants.Messages;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

@Component
public class StartCommand extends BotCommand {
    private final String money24Url;

    public StartCommand(@Value("start") String command,
                        @Value("Starts the bot") String description,
                        @Value("${spring.cloud.openfeign.client.config.money24Client.url}") String money24Url) {
        super(command, description);
        this.money24Url = money24Url;
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        var sendMessage = SendMessage.builder()
                .chatId(chat.getId().toString())
                .text(String.format(Messages.START, money24Url))
                .parseMode(ParseMode.MARKDOWN)
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .resizeKeyboard(true)
                        .isPersistent(true)
                        .keyboardRow(new KeyboardRow(List.of(
                                new KeyboardButton(Messages.CURRENT_RATE),
                                new KeyboardButton(Messages.SUBSCRIBE)
                        )))
                        .build())
                .build();
        absSender.execute(sendMessage);
    }
}
