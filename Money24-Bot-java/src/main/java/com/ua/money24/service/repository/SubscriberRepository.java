package com.ua.money24.service.repository;

import com.ua.money24.entity.Subscriber;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {
    List<Subscriber> findAllByRegionIdAndCurrenciesId(Integer regionId, Integer currenciesId);
}
