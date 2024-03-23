package com.ua.money24.service.converter;

import com.ua.money24.model.Currency;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CurrencyToEntityFunction implements Function<Currency, com.ua.money24.entity.Currency> {
    @Override
    public com.ua.money24.entity.Currency apply(Currency currency) {
        return new com.ua.money24.entity.Currency()
                .setId(currency.id())
                .setCurrencyCode(currency.code())
                .setFlag(currency.flag());
    }
}
