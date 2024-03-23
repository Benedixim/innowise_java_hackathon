package com.ua.money24.service.converter;

import com.ua.money24.entity.Currency;
import com.ua.money24.entity.Region;
import com.ua.money24.model.Rate;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RateToEntityConverter implements Function<Rate, com.ua.money24.entity.Rate> {
    @Override
    public com.ua.money24.entity.Rate apply(Rate rate) {
        return new com.ua.money24.entity.Rate()
                .setId(rate.id())
                .setRegion(new Region().setId(rate.regionId()))
                .setCurrency(new Currency().setId(rate.currencyId()))
                .setBuyRate(rate.buyRate())
                .setSellRate(rate.sellRate());
    }
}
