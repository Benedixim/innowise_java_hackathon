package com.ua.money24.service.provider.region;

import com.ua.money24.model.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegionProviderImpl implements RegionProvider {
    private final Integer regionId;

    public RegionProviderImpl(@Value("${application.region}") Integer regionId) {
        this.regionId = regionId;
    }

    @Override
    public List<Region> getRegions() {
        return List.of(
                new Region(regionId)
        );
    }
}
