package com.ua.money24.bot.command;

import com.ua.money24.constants.CurrencyActions;
import com.ua.money24.constants.Messages;
import com.ua.money24.helper.CurrencyCallbackData;
import com.ua.money24.helper.InlineKeyboardMarkupWrapper;
import com.ua.money24.model.Rate;
import com.ua.money24.service.provider.currency.CurrencyProvider;
import com.ua.money24.service.provider.rate.RateProvider;
import com.ua.money24.service.provider.subscriber.SubscriberProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.function.Function;

@Log4j2
@Component
public class CurrentRateCommand extends BaseTextCommand {
    private final RateProvider rateProvider;
    private final CurrencyProvider currencyProvider;
    private final SubscriberProvider subscriberProvider;
    private final Function<Rate, String> rateStringFunction;
    private final Integer defaultCurrencyId;

    public CurrentRateCommand(RateProvider internalRateProvider,
                              CurrencyProvider currencyProvider,
                              SubscriberProvider subscriberProvider,
                              Function<Rate, String> rateStringFunction,
                              @Value(Messages.CURRENT_RATE) String commandIdentifier,
                              @Value("Returns current exchange rates") String description,
                              @Value("${application.default-currency-id:0}") Integer defaultCurrencyId) {
        super(commandIdentifier, description);
        this.rateProvider = internalRateProvider;
        this.currencyProvider = currencyProvider;
        this.subscriberProvider = subscriberProvider;
        this.rateStringFunction = rateStringFunction;

        this.defaultCurrencyId = defaultCurrencyId;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        BotApiMethod<?> method;
        var chatId = message.getChatId();
        var subscriber = subscriberProvider.getOrCreateById(chatId);
        var replyMarkup = createCurrencyKeyboard();
        var currencyId = arguments.length > 0 ? Integer.parseInt(arguments[0]) : defaultCurrencyId;
        var selectedCurrency = currencyProvider.getCurrencyById(currencyId);
        var rate = rateProvider.getRateByRegionAndCurrency(subscriber.regionId(), selectedCurrency.id());
        var text = rateStringFunction.apply(rate);
        if (arguments.length > 0) {
            method = EditMessageText.builder()
                    .messageId(message.getMessageId())
                    .chatId(chatId)
                    .text(text)
                    .replyMarkup(replyMarkup)
                    .build();
        } else {
            method = SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .replyMarkup(replyMarkup)
                    .build();
        }


        try {
            absSender.execute(method);
        } catch (TelegramApiRequestException exception) {
            log.warn(exception);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private InlineKeyboardMarkup createCurrencyKeyboard() {
        var currencies = currencyProvider.getAll();
        return new InlineKeyboardMarkupWrapper(4).add(
                currencies.stream()
                        .map(currency -> InlineKeyboardButton.builder()
                                .text(currency.flag())
                                .callbackData(new CurrencyCallbackData(
                                        currency.id().toString(),
                                        CurrencyActions.RATES
                                ).toString())
                                .build())
                        .toArray(InlineKeyboardButton[]::new)
        );
    }
}
