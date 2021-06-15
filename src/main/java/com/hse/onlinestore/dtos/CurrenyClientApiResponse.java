package com.hse.onlinestore.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrenyClientApiResponse {

    private boolean success;

    private CurrencyQuery query;

    private CurrencyInfo info;

    private CurrencyError error;

    private String date;

    private String result;
}
