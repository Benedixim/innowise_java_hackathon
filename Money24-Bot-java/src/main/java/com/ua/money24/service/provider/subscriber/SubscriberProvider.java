package com.ua.money24.service.provider.subscriber;

import com.ua.money24.model.Subscriber;

import java.util.List;

public interface SubscriberProvider {
    Subscriber getOrCreateById(Long id);
    List<Subscriber> getSubscribersByRegionAndCurrency(Integer regionId, Integer currencyId);

    void update(Subscriber subscriber);
}
