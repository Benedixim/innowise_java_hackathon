package com.ua.money24.service.provider.rate;

import com.ua.money24.model.Rate;
import com.ua.money24.service.repository.RateRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class InternalRateProvider implements RateProvider {
    private final RateRepository rateRepository;

    private final Function<com.ua.money24.entity.Rate, Rate> rateFunction;

    public InternalRateProvider(RateRepository rateRepository,
                                Function<com.ua.money24.entity.Rate, Rate> rateFunction) {
        this.rateRepository = rateRepository;
        this.rateFunction = rateFunction;
    }

    @Override
    public List<Rate> getRatesInRegion(Integer regionId) {
        return rateRepository.findAllByRegionId(regionId)
                .stream()
                .map(rateFunction)
                .collect(Collectors.toList());
    }

    @Override
    public Rate getRateByRegionAndCurrency(Integer regionId, Integer currency) {
        return rateRepository.findByRegionIdAndCurrencyId(regionId, currency)
                .map(rateFunction)
                .orElse(null);
    }
}
