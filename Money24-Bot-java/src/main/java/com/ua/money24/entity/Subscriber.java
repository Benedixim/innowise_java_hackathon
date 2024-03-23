package com.ua.money24.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Entity
@Accessors(chain = true)
public class Subscriber {
    @Id
    private Long id;
    @ManyToOne
    private Region region;

    @ManyToMany
    @JoinTable(
            name = "subscriber_currencies",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "currency_id")
    )
    private List<Currency> currencies;
}
