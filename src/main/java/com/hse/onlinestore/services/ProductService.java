package com.hse.onlinestore.services;

import java.util.List;

import com.hse.onlinestore.dtos.ProductDto;
import com.hse.onlinestore.exceptions.CategoryNotFoundException;
import com.hse.onlinestore.exceptions.NotSupportedCurrencyException;
import com.hse.onlinestore.exceptions.ProductNotFoundException;

public interface ProductService {

    ProductDto addProduct(ProductDto product) throws CategoryNotFoundException, NotSupportedCurrencyException;

    ProductDto findProduct(Long productId) throws ProductNotFoundException;

    ProductDto updateProduct(Long productId,
                             ProductDto updatedProduct) throws ProductNotFoundException, CategoryNotFoundException, NotSupportedCurrencyException;

    void deleteProduct(Long productId);

    List<ProductDto> findAllProducts();
}
