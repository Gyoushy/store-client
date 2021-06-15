package com.hse.onlinestore.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyInfo {

    private String timestamp;

    private BigDecimal rate;
}
