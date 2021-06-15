package com.hse.onlinestore.repositories;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;
import com.hse.onlinestore.model.Category;
import com.hse.onlinestore.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.hse.onlinestore.TestUtils.CATEGORY_DESCRIPTION;
import static com.hse.onlinestore.TestUtils.CATEGORY_NAME;
import static com.hse.onlinestore.TestUtils.PRODUCT_DESCRIPTION;
import static com.hse.onlinestore.TestUtils.PRODUCT_NAME;
import static com.hse.onlinestore.TestUtils.createModelCategory;
import static com.hse.onlinestore.TestUtils.createModelProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ProductCategoryRepositoriesTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testSaveProduct() {

        Category category = createModelCategory();
        categoryRepository.save(category);

        List<Category> testCategoryList = Lists.newArrayList(categoryRepository.findAll());
        int categoryId = testCategoryList.stream().filter(c -> CATEGORY_NAME.equals(c.getName())).findFirst().get().getId();

        Category testCategory = categoryRepository.findById(categoryId).get();
        assertEquals(CATEGORY_NAME, testCategory.getName());
        assertEquals(CATEGORY_DESCRIPTION, testCategory.getDescription());

        Product product = createModelProduct(category);

        productRepository.save(product);

        List<Product> testProductList = Lists.newArrayList(productRepository.findAll());
        long productId = testProductList.stream().filter(p -> PRODUCT_NAME.equals(p.getName())).findFirst().get().getId();

        Product testProduct = productRepository.findById(productId).get();
        assertEquals(PRODUCT_NAME, testProduct.getName());
        assertEquals(PRODUCT_DESCRIPTION, testProduct.getDescription());
        assertEquals(categoryId, testProduct.getCategory().getId());

        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}
