package com.hse.onlinestore.clients;

import java.math.BigDecimal;

import com.hse.onlinestore.dtos.CurrencyDto;

public interface CurrencyClient {

    BigDecimal getPriceInEuro(BigDecimal price, CurrencyDto currency);
}
