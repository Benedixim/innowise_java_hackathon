package com.ua.money24.service.converter;

import com.ua.money24.entity.Currency;
import com.ua.money24.entity.Subscriber;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EntityToSubscriberConverter implements Function<Subscriber, com.ua.money24.model.Subscriber> {
    private final Function<Currency, com.ua.money24.model.Currency> currencyFunction;

    public EntityToSubscriberConverter(Function<Currency, com.ua.money24.model.Currency> currencyFunction) {
        this.currencyFunction = currencyFunction;
    }

    @Override
    public com.ua.money24.model.Subscriber apply(Subscriber subscriber) {
        List<com.ua.money24.model.Currency> currencies = new ArrayList<>();
        try {
            currencies = subscriber.getCurrencies()
                    .stream()
                    .map(currencyFunction)
                    .collect(Collectors.toList());
        } catch (LazyInitializationException ignored) {

        }
        return new com.ua.money24.model.Subscriber(subscriber.getId(), subscriber.getRegion().getId(), currencies);
    }
}
