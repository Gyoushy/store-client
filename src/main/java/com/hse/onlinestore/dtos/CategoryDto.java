package com.hse.onlinestore.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoryDto {

    public CategoryDto() {
        products = new ArrayList<>();
    }

    private int id;

    @NotNull
    @Size(min = 1, max = 500)
    private String name;

    @NotNull
    @Size(min = 1, max = 5000)
    private String description;

    private List<ProductDto> products;

    public void addProduct(ProductDto productDto) {
        products.add(productDto);
    }
}
