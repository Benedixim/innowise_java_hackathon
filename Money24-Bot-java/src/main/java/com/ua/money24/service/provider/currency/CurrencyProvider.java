package com.ua.money24.service.provider.currency;

import com.ua.money24.model.Currency;

import java.util.List;

public interface CurrencyProvider {
    Currency getCurrencyById(Integer id);

    List<Currency> getAll();
}
