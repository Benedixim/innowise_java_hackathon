package com.ua.money24.service.observer;

import com.ua.money24.model.Rate;
import com.ua.money24.model.Subscriber;
import com.ua.money24.service.provider.subscriber.SubscriberProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.BiFunction;

@Log4j2
@Component
public class TelegramRateObserver implements RateObserver {
    private final AbsSender absSender;
    private final SubscriberProvider subscriberProvider;
    private final BiFunction<Rate, Rate, String> rateChangesToMessageFunction;

    public TelegramRateObserver(AbsSender absSender, SubscriberProvider subscriberProvider,
                                BiFunction<Rate, Rate, String> rateChangesToMessageFunction) {
        this.absSender = absSender;
        this.subscriberProvider = subscriberProvider;
        this.rateChangesToMessageFunction = rateChangesToMessageFunction;
    }


    @Override
    public void observe(@Nullable Rate prevRate, Rate newRate) {
        if (prevRate == null) {
            return;
        }
        var subscribers = subscriberProvider.getSubscribersByRegionAndCurrency(
                newRate.regionId(),
                newRate.currencyId()
        );
        var text = rateChangesToMessageFunction.apply(prevRate, newRate);
        for (Subscriber subscriber : subscribers) {
            try {
                var sendMessage = SendMessage.builder()
                        .chatId(subscriber.id())
                        .text(text)
                        .build();
                absSender.execute(sendMessage);
            } catch (TelegramApiException ex) {
                log.warn("Failed to send message to subscriber with id: {}", subscriber.id(), ex);
            }
        }
    }
}
