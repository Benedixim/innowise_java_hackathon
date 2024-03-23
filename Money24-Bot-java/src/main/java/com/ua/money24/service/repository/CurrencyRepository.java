package com.ua.money24.service.repository;

import com.ua.money24.entity.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface CurrencyRepository extends ListCrudRepository<Currency, Integer> {
}
