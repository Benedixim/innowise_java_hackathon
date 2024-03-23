package com.ua.money24.service.converter;

import com.ua.money24.constants.Messages;
import com.ua.money24.model.Rate;
import com.ua.money24.service.provider.currency.CurrencyProvider;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RateToMessageConverter implements Function<Rate, String> {
    private final CurrencyProvider currencyProvider;

    public RateToMessageConverter(CurrencyProvider currencyProvider) {
        this.currencyProvider = currencyProvider;
    }

    @Override
    public String apply(Rate rate) {
        var currency = currencyProvider.getCurrencyById(rate.currencyId());
        return String.format(
                Messages.RATE_TEMPLATE,
                currency,
                rate.buyRate(),
                rate.sellRate()
        );
    }
}
