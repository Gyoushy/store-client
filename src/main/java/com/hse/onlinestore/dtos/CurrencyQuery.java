package com.hse.onlinestore.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyQuery {

    private String from;

    private String to;

    private BigDecimal amount;
}
