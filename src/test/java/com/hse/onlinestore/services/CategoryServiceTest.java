package com.hse.onlinestore.services;

import java.math.BigDecimal;
import java.util.List;

import com.hse.onlinestore.dtos.CategoryDto;
import com.hse.onlinestore.dtos.CurrencyDto;
import com.hse.onlinestore.dtos.ProductDto;
import com.hse.onlinestore.exceptions.CategoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.hse.onlinestore.TestUtils.CATEGORY_DESCRIPTION;
import static com.hse.onlinestore.TestUtils.CATEGORY_NAME;
import static com.hse.onlinestore.TestUtils.PRODUCT_DESCRIPTION;
import static com.hse.onlinestore.TestUtils.PRODUCT_NAME;
import static com.hse.onlinestore.TestUtils.createCategory;
import static com.hse.onlinestore.TestUtils.createProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CategoryServiceTest {

    @Autowired
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @MockBean
    private CurrencyService currencyService;

    private CategoryDto category;

    @BeforeEach
    public void setup() {
        category = createCategory(CATEGORY_NAME, CATEGORY_DESCRIPTION);
        category.addProduct(createProduct(CurrencyDto.USD, PRODUCT_NAME, PRODUCT_DESCRIPTION,
                                          BigDecimal.valueOf(50), 3, 5, false, 14, BigDecimal.valueOf(5)));
        category = categoryService.addCategory(category);
    }

    @Test
    void listCategoriesTest() {
        List<CategoryDto> categoriesList = categoryService.listCategories();
        assertTrue(categoriesList.size() > 0);
    }

    @Test
    void viewCategoryTest() throws CategoryNotFoundException {
        when(currencyService.getPriceInEuro(BigDecimal.valueOf(50), CurrencyDto.USD)).thenReturn(BigDecimal.valueOf(55));
        int categoryId = category.getId();

        CategoryDto actualCategory = categoryService.getCategory(categoryId);
        assertCategory(actualCategory, category);
    }

    @Test
    void editCategoryTest() throws CategoryNotFoundException {
        when(currencyService.getPriceInEuro(BigDecimal.valueOf(50), CurrencyDto.USD)).thenReturn(BigDecimal.valueOf(55));

        CategoryDto updatedCategory = createCategory("New Category Name", "New Category Description");
        updatedCategory.setId(category.getId());
        updatedCategory.addProduct(createProduct(CurrencyDto.USD, "new Product Name",
                                                 "new product description",
                                                 BigDecimal.valueOf(60), 4, 6, true, 16, null));
        categoryService.updateCategory(category.getId(), updatedCategory);

        CategoryDto actualCategory = categoryService.getCategory(category.getId());
        assertCategory(updatedCategory, actualCategory);
    }

    @Test
    void deleteProductTest() {
        int categoryId = category.getId();
        categoryService.deleteCategory(categoryId);
        CategoryNotFoundException thrown = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.getCategory(categoryId));

        assertTrue(thrown.getMessage().contains(("Category Not Found")));
    }

    private void assertCategory(CategoryDto actualCategory, CategoryDto expectedCategory) {
        assertEquals(actualCategory.getId(), expectedCategory.getId());
        assertEquals(actualCategory.getName(), expectedCategory.getName());
        assertEquals(actualCategory.getDescription(), expectedCategory.getDescription());
        assertEquals(actualCategory.getProducts().size(), expectedCategory.getProducts().size());
    }
}
