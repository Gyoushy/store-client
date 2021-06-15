package com.hse.onlinestore.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyError {

    private int code;

    private String type;

    private String info;
}
