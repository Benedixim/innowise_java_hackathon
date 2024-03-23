package com.ua.money24.service.repository;

import com.ua.money24.entity.Rate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends CrudRepository<Rate, Integer> {
    List<Rate> findAllByRegionId(Integer regionId);

    Optional<Rate> findByRegionIdAndCurrencyId(Integer regionId, Integer currencyId);
}
