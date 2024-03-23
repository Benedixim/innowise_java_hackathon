package com.ua.money24.service.provider.subscriber;

import com.ua.money24.model.Subscriber;
import com.ua.money24.service.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Component
public class SubscriberProviderImpl implements SubscriberProvider {
    private final SubscriberRepository subscriberRepository;
    private final Function<com.ua.money24.entity.Subscriber, Subscriber> entitySubscriberFunction;

    private final Function<Subscriber, com.ua.money24.entity.Subscriber> subscriberEntityFunction;
    private final Integer regionId;

    public SubscriberProviderImpl(SubscriberRepository subscriberRepository,
                                  Function<com.ua.money24.entity.Subscriber, Subscriber> entitySubscriberFunction,
                                  Function<Subscriber, com.ua.money24.entity.Subscriber> subscriberEntityFunction,
                                  @Value("${application.region}") Integer regionId) {
        this.subscriberRepository = subscriberRepository;
        this.entitySubscriberFunction = entitySubscriberFunction;
        this.subscriberEntityFunction = subscriberEntityFunction;
        this.regionId = regionId;
    }


    @Override
    @Transactional // loads subscriber currencies
    public Subscriber getOrCreateById(Long id) {
        var entity = subscriberRepository.findById(id)
                .orElseGet(() -> subscriberRepository.save(
                        subscriberEntityFunction.apply(new Subscriber(id, regionId, List.of()))
                ));
        return entitySubscriberFunction.apply(entity);
    }

    @Override
    public List<Subscriber> getSubscribersByRegionAndCurrency(Integer regionId, Integer currencyId) {
        return subscriberRepository.findAllByRegionIdAndCurrenciesId(regionId, currencyId)
                .stream()
                .map(entitySubscriberFunction)
                .toList();
    }

    @Override
    @Transactional
    public void update(Subscriber subscriber) {
        subscriberRepository.save(subscriberEntityFunction.apply(subscriber));
    }
}
