package com.hse.onlinestore;

import java.math.BigDecimal;

import com.hse.onlinestore.dtos.CategoryDto;
import com.hse.onlinestore.dtos.CurrencyDto;
import com.hse.onlinestore.dtos.ProductDto;
import com.hse.onlinestore.model.Category;
import com.hse.onlinestore.model.Product;

public class TestUtils {

    public static final String CATEGORY_NAME = "cat name";

    public static final String CATEGORY_DESCRIPTION = "cat description";

    public static final String PRODUCT_NAME = "product name";

    public static final String PRODUCT_DESCRIPTION = "product description";

    public static ProductDto createProduct(CurrencyDto currency, String productName, String productDesc,
                                           BigDecimal price,
                                           int rangeFrom, int rangeTo, boolean freeShipping, int freeReturnDays,
                                           BigDecimal shippingPrice) {

        ProductDto product = new ProductDto();
        product.setName(productName);
        product.setDescription(productDesc);
        product.setPrice(price);
        product.setDeliveryRangeFrom(rangeFrom);
        product.setDeliveryRangeTo(rangeTo);
        product.setFreeShipping(freeShipping);
        product.setNumberOfDaysForFreeReturn(freeReturnDays);
        product.setShippingPrice(shippingPrice);
        product.setCurrency(currency);
        return product;
    }

    public static CategoryDto createCategory(String categoryName, String categoryDescription) {

        CategoryDto category = new CategoryDto();
        category.setName(categoryName);
        category.setDescription(categoryDescription);
        return category;
    }

    public static Product createModelProduct(Category category) {
        Product product = new Product();
        product.setName(PRODUCT_NAME);
        product.setDescription(PRODUCT_DESCRIPTION);
        product.setCurrency("EUR");
        product.setPrice(BigDecimal.valueOf(50));
        product.setCategory(category);
        product.setDeliveryRangeFrom(3);
        product.setDeliveryRangeTo(5);
        product.setFreeShipping(true);
        product.setNumberOfDaysForFreeReturn(14);
        product.setPriceInEuro(BigDecimal.valueOf(50));
        product.setShippingPrice(BigDecimal.valueOf(5));
        return product;
    }

    public static Category createModelCategory() {
        Category category = new Category();
        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);
        return category;
    }
}
