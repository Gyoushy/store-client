package com.hse.onlinestore.services;

import java.math.BigDecimal;

import com.hse.onlinestore.clients.CurrencyClient;
import com.hse.onlinestore.dtos.CurrencyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyClient currencyClient;

    @Autowired
    public CurrencyServiceImpl(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    @Override public BigDecimal getPriceInEuro(BigDecimal price, CurrencyDto currency) {
        return currencyClient.getPriceInEuro(price, currency);
    }
}
