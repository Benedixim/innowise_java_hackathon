package com.ua.money24.service.converter;

import com.ua.money24.entity.Region;
import com.ua.money24.model.Currency;
import com.ua.money24.model.Subscriber;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SubscriberToEntityConverter implements Function<Subscriber, com.ua.money24.entity.Subscriber> {
    private final Function<Currency, com.ua.money24.entity.Currency> currencyFunction;

    public SubscriberToEntityConverter(Function<Currency, com.ua.money24.entity.Currency> currencyFunction) {
        this.currencyFunction = currencyFunction;
    }

    @Override
    public com.ua.money24.entity.Subscriber apply(Subscriber subscriber) {
        var currencies = subscriber.currencies()
                .stream()
                .map(currencyFunction)
                .toList();
        return new com.ua.money24.entity.Subscriber()
                .setId(subscriber.id())
                .setRegion(new Region().setId(subscriber.regionId()))
                .setCurrencies(currencies);
    }
}
