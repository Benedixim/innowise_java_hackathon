package com.ua.money24.service.converter;

import com.ua.money24.entity.Currency;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EntityToCurrencyConverter implements Function<Currency, com.ua.money24.model.Currency> {

    @Override
    public com.ua.money24.model.Currency apply(Currency currency) {
        return new com.ua.money24.model.Currency(
                currency.getId(),
                currency.getCurrencyCode(),
                currency.getFlag()
        );
    }
}
