package com.ua.money24.service.converter;

import com.ua.money24.constants.Messages;
import com.ua.money24.constants.RateType;
import com.ua.money24.helper.RateHelper;
import com.ua.money24.model.Rate;
import com.ua.money24.service.provider.currency.CurrencyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

@Component
public class RateChangesToMessageConverter implements BiFunction<Rate, Rate, String> {

    private final CurrencyProvider currencyProvider;
    private final Integer decimalPlaces;


    public RateChangesToMessageConverter(CurrencyProvider currencyProvider,
                                         @Value("${application.rate.decimal-places:2}") Integer decimalPlaces) {
        this.currencyProvider = currencyProvider;
        this.decimalPlaces = decimalPlaces;
    }

    @Override
    public String apply(Rate prevRate, Rate newRate) {
        var buyDiff = subtract(prevRate.buyRate(), newRate.buyRate());
        var sellDiff = subtract(prevRate.sellRate(), newRate.sellRate());

        var currency = currencyProvider.getCurrencyById(newRate.currencyId());
        return String.format(
                Messages.RATE_TEMPLATE,
                String.join( " ", currency.toString(), Messages.RATE_CHANGED),
                String.join(" ", newRate.buyRate().toString(), RateHelper.diffToText(buyDiff, RateType.BUY)),
                String.join(" ", newRate.sellRate().toString(), RateHelper.diffToText(sellDiff, RateType.SELL))
        );
    }

    private double subtract(double prevRate, double newRate) {
        return BigDecimal.valueOf(newRate)
                .subtract(BigDecimal.valueOf(prevRate))
                .setScale(decimalPlaces, RoundingMode.HALF_EVEN)
                .doubleValue();
    }
}
