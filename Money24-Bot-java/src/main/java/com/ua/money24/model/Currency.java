package com.ua.money24.model;

import org.springframework.util.StringUtils;

import java.util.Locale;

public record Currency(Integer id, String code, String flag) {
    @Override
    public String toString() {
        var locale = Locale.getDefault();
        var currencyInstance = java.util.Currency.getInstance(this.code);
        var currencyName = StringUtils.capitalize(currencyInstance.getDisplayName(locale));
        return String.join(" ", currencyName, this.flag());
    }
}
