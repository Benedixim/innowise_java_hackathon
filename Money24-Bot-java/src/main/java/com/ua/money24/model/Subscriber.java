package com.ua.money24.model;

import java.util.List;

public record Subscriber(Long id, Integer regionId, List<Currency> currencies) {
}
