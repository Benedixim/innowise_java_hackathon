package com.ua.money24.service.provider.rate;

import com.ua.money24.model.Rate;

import java.util.List;

public interface RateProvider {
    List<Rate> getRatesInRegion(Integer regionId);
    
    Rate getRateByRegionAndCurrency(Integer regionId, Integer currency);
}
