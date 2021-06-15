package com.hse.onlinestore.services;

import java.math.BigDecimal;

import com.hse.onlinestore.dtos.CurrencyDto;

public interface CurrencyService {

    BigDecimal getPriceInEuro(BigDecimal price, CurrencyDto currency);
}
