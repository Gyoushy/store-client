package com.hse.onlinestore.mappers;

import com.hse.onlinestore.dtos.CurrencyDto;
import org.mapstruct.Named;

@Named("MapperUtils")
public class MapperUtils {

    private MapperUtils() {
    }

    @Named("currencyToString")
    public static String currencyToString(CurrencyDto currencyDto) {
        return currencyDto.name();
    }
}
