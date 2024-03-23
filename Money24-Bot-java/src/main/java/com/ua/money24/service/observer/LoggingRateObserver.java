package com.ua.money24.service.observer;

import com.ua.money24.model.Rate;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LoggingRateObserver implements RateObserver {
    @Override
    public void observe(Rate prevRate, Rate newRate) {
        log.info("Rate changed: {}", newRate);
    }
}
