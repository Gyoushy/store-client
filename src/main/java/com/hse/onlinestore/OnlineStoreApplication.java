package com.hse.onlinestore;

import java.math.BigDecimal;

import com.hse.onlinestore.model.Category;
import com.hse.onlinestore.model.Product;
import com.hse.onlinestore.repositories.CategoryRepository;
import com.hse.onlinestore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineStoreApplication implements CommandLineRunner {

    @Autowired
    public OnlineStoreApplication(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(OnlineStoreApplication.class, args);
    }

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override public void run(String... args) throws Exception {
        Category category = createCategory();
        categoryRepository.save(category);
        Product product = createProduct(category);
        Product product1 = createProduct(category);
        productRepository.save(product);
        productRepository.save(product1);
    }

    private Category createCategory() {
        Category category = new Category();
        category.setName("CATEGORY_NAME");
        category.setDescription("CATEGORY_DESCRIPTION");
        return category;
    }

    private Product createProduct(Category category) {
        Product product = new Product();
        product.setName("PRODUCT_NAME");
        product.setDescription("PRODUCT_DESCRIPTION");
        product.setCategory(category);
        product.setDeliveryRangeFrom(3);
        product.setDeliveryRangeTo(5);
        product.setFreeShipping(true);
        product.setNumberOfDaysForFreeReturn(14);
        product.setPriceInEuro(BigDecimal.valueOf(50));
        product.setPrice(BigDecimal.valueOf(50));
        product.setCurrency("EUR");
        product.setShippingPrice(BigDecimal.valueOf(5));
        return product;
    }
}
