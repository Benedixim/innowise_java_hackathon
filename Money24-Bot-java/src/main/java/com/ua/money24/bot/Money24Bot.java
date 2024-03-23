package com.ua.money24.bot;

import com.ua.money24.config.TelegramBotProperties;
import com.ua.money24.constants.CurrencyActions;
import com.ua.money24.constants.Messages;
import com.ua.money24.helper.CurrencyCallbackData;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
public class Money24Bot extends TelegramLongPollingCommandBot {
    private final String botUsername;

    public Money24Bot(TelegramBotProperties telegramBotProperties) {
        super(telegramBotProperties.token());
        this.botUsername = telegramBotProperties.username();
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var command = getRegisteredCommand(update.getMessage().getText());
            if (Objects.isNull(command)) {
                return;
            }
            command.processMessage(this, update.getMessage(), new String[]{});
        } else if (update.hasCallbackQuery()) {
            processCallbackQuery(update.getCallbackQuery());
        }
    }

    private void processCallbackQuery(CallbackQuery query) {
        if (CurrencyCallbackData.filter(query.getData())) {
            var data = CurrencyCallbackData.parse(query.getData());
            var currencyId = data.get(CurrencyCallbackData.ID);
            var action = data.get(CurrencyCallbackData.ACTION);
            var commandIdentifier = resolveTextCommand(action);
            var command = getRegisteredCommand(commandIdentifier);
            if (Objects.isNull(command)) {
                return;
            }
            command.processMessage(
                    this,
                    query.getMessage(),
                    new String[]{currencyId}
            );
        }
    }

    private String resolveTextCommand(String action) {
        return switch (action) {
            case CurrencyActions.RATES -> Messages.CURRENT_RATE;
            case CurrencyActions.SUBSCRIBE -> Messages.SUBSCRIBE;
            default -> null;
        };
    }
}
