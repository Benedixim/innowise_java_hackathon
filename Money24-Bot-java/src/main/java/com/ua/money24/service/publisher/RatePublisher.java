package com.ua.money24.service.publisher;

import com.ua.money24.model.Rate;

public interface RatePublisher {
    void publish(Rate prevRate, Rate newRate);
}
