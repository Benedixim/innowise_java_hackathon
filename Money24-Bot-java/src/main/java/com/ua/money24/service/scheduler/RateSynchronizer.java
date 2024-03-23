package com.ua.money24.service.scheduler;

import com.ua.money24.service.provider.rate.RateProvider;
import com.ua.money24.service.provider.region.RegionProvider;
import com.ua.money24.service.publisher.RatePublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RateSynchronizer {
    private final RateProvider money24RateProvider;
    private final RateProvider internalRateProvider;
    private final RegionProvider regionProvider;


    private final RatePublisher ratePublisher;


    public RateSynchronizer(RateProvider money24RateProvider, RateProvider internalRateProvider,
                            RegionProvider regionProvider, RatePublisher ratePublisher) {
        this.money24RateProvider = money24RateProvider;
        this.internalRateProvider = internalRateProvider;
        this.regionProvider = regionProvider;
        this.ratePublisher = ratePublisher;
    }

    @Scheduled(fixedRateString = "${application.sync-rate-seconds}", timeUnit = TimeUnit.SECONDS)
    public void syncRates() {
        regionProvider.getRegions().forEach(region -> {
            var money24Rates = money24RateProvider.getRatesInRegion(region.id());
            var internalRates = internalRateProvider.getRatesInRegion(region.id());

            money24Rates.removeAll(internalRates);

            money24Rates.forEach(newRate -> ratePublisher.publish(
                    internalRates.stream()
                            .filter(rate -> rate.id() == newRate.id())
                            .findFirst()
                            .orElse(null),
                    newRate
            ));
        });
    }
}