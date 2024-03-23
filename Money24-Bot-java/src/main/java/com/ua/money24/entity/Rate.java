package com.ua.money24.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
public class Rate {
    @Id
    private Integer id;
    @ManyToOne
    private Region region;
    @ManyToOne
    private Currency currency;
    private Double buyRate;
    private Double sellRate;
}
