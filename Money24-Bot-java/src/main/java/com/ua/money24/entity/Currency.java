package com.ua.money24.entity;

import com.ua.money24.constants.Emojis;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
public class Currency {
    @Id
    private Integer id;
    private String currencyCode;
    private String flag = Emojis.WHITE_FLAG;
}
