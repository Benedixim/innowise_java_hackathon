package com.ua.money24.service.converter;

import com.ua.money24.entity.Rate;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EntityToRateConverter implements Function<Rate, com.ua.money24.model.Rate> {
    @Override
    public com.ua.money24.model.Rate apply(Rate rate) {
        return new com.ua.money24.model.Rate(
                rate.getId(),
                rate.getRegion().getId(),
                rate.getCurrency().getId(),
                rate.getBuyRate(),
                rate.getSellRate()
        );
    }
}
