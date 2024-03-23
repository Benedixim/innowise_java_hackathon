package com.ua.money24.service.publisher;

import com.ua.money24.model.Rate;
import com.ua.money24.service.observer.RateObserver;
import org.springframework.stereotype.Component;

@Component
public class RatePublisherImpl implements RatePublisher {
    private final RateObserver[] rateObservers;

    public RatePublisherImpl(RateObserver... rateObservers) {
        this.rateObservers = rateObservers;
    }


    @Override
    public void publish(Rate prevRate, Rate newRate) {
        for (RateObserver rateObserver : rateObservers) {
            rateObserver.observe(prevRate, newRate);
        }
    }
}
