package com.ua.money24.service.provider.currency;

import com.ua.money24.model.Currency;
import com.ua.money24.service.repository.CurrencyRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class CurrencyProviderImpl implements CurrencyProvider {
    private final CurrencyRepository currencyRepository;
    private final Function<com.ua.money24.entity.Currency, Currency> currencyFunction;

    public CurrencyProviderImpl(CurrencyRepository currencyRepository,
                                Function<com.ua.money24.entity.Currency, Currency> currencyFunction) {
        this.currencyRepository = currencyRepository;
        this.currencyFunction = currencyFunction;
    }

    @Override
    public Currency getCurrencyById(Integer id) {
        return currencyRepository.findById(id)
                .map(currencyFunction)
                .orElse(null);
    }

    @Override
    public List<Currency> getAll() {
        return currencyRepository.findAll()
                .stream()
                .map(currencyFunction)
                .toList();
    }
}
