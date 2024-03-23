package com.ua.money24.bot.command;

import com.ua.money24.constants.CurrencyActions;
import com.ua.money24.constants.Emojis;
import com.ua.money24.constants.Messages;
import com.ua.money24.helper.CurrencyCallbackData;
import com.ua.money24.helper.InlineKeyboardMarkupWrapper;
import com.ua.money24.model.Currency;
import com.ua.money24.service.provider.currency.CurrencyProvider;
import com.ua.money24.service.provider.subscriber.SubscriberProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.List;

@Log4j2
@Component
public class SubscribeCommand extends BaseTextCommand {
    private final SubscriberProvider subscriberProvider;
    private final CurrencyProvider currencyProvider;

    protected SubscribeCommand(SubscriberProvider subscriberProvider,
                               CurrencyProvider currencyProvider,
                               @Value(Messages.SUBSCRIBE) String command,
                               @Value("Subscribe on currency") String description) {
        super(command, description);
        this.subscriberProvider = subscriberProvider;
        this.currencyProvider = currencyProvider;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        var subscriber = subscriberProvider.getOrCreateById(message.getChatId());
        var subscriberCurrencies = subscriber.currencies();
        var chatId = message.getChatId();
        BotApiMethod<?> method;
        try {
            if (arguments.length > 0) {
                var currencyId = Integer.parseInt(arguments[0]);
                var currency = currencyProvider.getCurrencyById(currencyId);
                var __ = subscriberCurrencies.contains(currency)
                        ? subscriberCurrencies.remove(currency)
                        : subscriberCurrencies.add(currency);
                subscriberProvider.update(subscriber);
                method = EditMessageText.builder()
                        .messageId(message.getMessageId())
                        .chatId(chatId)
                        .text(Messages.SUBSCRIBE_CURRENCIES)
                        .replyMarkup(createCurrencyKeyboard(subscriberCurrencies))
                        .build();
            } else {
                method = SendMessage.builder()
                        .chatId(chatId)
                        .text(Messages.SUBSCRIBE_CURRENCIES)
                        .replyMarkup(createCurrencyKeyboard(subscriberCurrencies))
                        .build();
            }
        } catch (NumberFormatException e) {
            method = DeleteMessage.builder()
                    .chatId(chatId)
                    .messageId(message.getMessageId())
                    .build();
        }
        try {
            absSender.execute(method);
        } catch (TelegramApiRequestException e) {
            log.warn(e);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private InlineKeyboardMarkup createCurrencyKeyboard(List<Currency> subscriberCurrencies) {
        var currencies = currencyProvider.getAll();
        return new InlineKeyboardMarkupWrapper(1).add(
                currencies.stream()
                        .map(currency -> InlineKeyboardButton.builder()
                                .text(String.join(
                                        " ",
                                        subscriberCurrencies.contains(currency) ? Emojis.BELL : "",
                                        currency.toString()
                                ))
                                .callbackData(new CurrencyCallbackData(
                                        currency.id().toString(),
                                        CurrencyActions.SUBSCRIBE
                                ).toString())
                                .build())
                        .toArray(InlineKeyboardButton[]::new)
        ).add(InlineKeyboardButton.builder()
                .text(Messages.FINISH)
                .callbackData(new CurrencyCallbackData(
                        "finish",
                        CurrencyActions.SUBSCRIBE
                ).toString())
                .build());
    }
}
