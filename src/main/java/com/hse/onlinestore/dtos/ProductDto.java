package com.hse.onlinestore.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 500)
    private String name;

    @NotNull
    @Size(min = 1, max = 5000)
    private String description;

    @NotNull
    private int categoryId;

    private String categoryName;

    @JsonIgnore
    private BigDecimal priceInEuro;

    @NotNull
    private BigDecimal price;

    @NotNull
    private CurrencyDto currency;

    @NotNull
    private int numberOfDaysForFreeReturn;

    @NotNull
    private boolean freeShipping;

    private BigDecimal shippingPrice;

    private int deliveryRangeFrom;

    private int deliveryRangeTo;
}
