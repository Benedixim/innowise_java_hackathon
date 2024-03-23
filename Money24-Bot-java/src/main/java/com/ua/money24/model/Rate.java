package com.ua.money24.model;

public record Rate(Integer id, Integer regionId, Integer currencyId, Double buyRate, Double sellRate) {
}
