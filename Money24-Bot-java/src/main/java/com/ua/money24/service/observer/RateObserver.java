package com.ua.money24.service.observer;

import com.ua.money24.model.Rate;
import org.springframework.lang.Nullable;

public interface RateObserver {
    void observe(@Nullable Rate prevRate, Rate newRate);
}
