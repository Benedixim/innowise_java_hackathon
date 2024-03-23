package com.ua.money24.service.provider.rate;

import com.ua.money24.client.Money24Client;
import com.ua.money24.constants.ExecTypes;
import com.ua.money24.constants.Methods;
import com.ua.money24.constants.RateType;
import com.ua.money24.exceptions.RatesNotFoundException;
import com.ua.money24.model.Rate;
import com.ua.money24.model.request.ExecAsPublicRequest;
import com.ua.money24.model.response.ExecAsPublicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Money24RateProvider implements RateProvider {
    private final Money24Client money24Client;

    public Money24RateProvider(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Money24Client money24Client
    ) {
        this.money24Client = money24Client;
    }

    @Override
    public List<Rate> getRatesInRegion(Integer regionId) {
        var response = money24Client.execAsPublic(new ExecAsPublicRequest(
                ExecTypes.GET,
                String.format(Methods.RATES, regionId),
                null
        ));
        if (response.statusCode() != HttpStatus.OK.value())
            throw new RatesNotFoundException();
        return response.result()
                .rates()
                .stream()
                .filter(rate -> rate.rate() > 0)
                .collect(Collectors.groupingBy(ExecAsPublicResponse.Result.Rate::id))
                .entrySet()
                .stream()
                .map(entry -> {
                    Integer id = entry.getKey();
                    var buyRate = findRateByType(entry.getValue(), RateType.BUY);
                    var sellRate = findRateByType(entry.getValue(), RateType.SELL);
                    return new Rate(
                            id,
                            buyRate.regionID(),
                            buyRate.currId(),
                            buyRate.rate(),
                            sellRate.rate()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public Rate getRateByRegionAndCurrency(Integer regionId, Integer currency) {
        return getRatesInRegion(regionId).stream()
                .filter(rate -> rate.currencyId() == currency)
                .findFirst()
                .orElse(null);
    }

    private ExecAsPublicResponse.Result.Rate findRateByType(List<ExecAsPublicResponse.Result.Rate> rates, String type) {
        return rates.stream()
                .filter(rate -> type.equals(rate.type()))
                .findFirst().orElse(null);
    }
}
