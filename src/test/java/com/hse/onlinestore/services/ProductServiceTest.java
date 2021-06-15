package com.hse.onlinestore.services;

import java.math.BigDecimal;
import java.util.List;

import com.hse.onlinestore.dtos.CategoryDto;
import com.hse.onlinestore.dtos.CurrencyDto;
import com.hse.onlinestore.dtos.ProductDto;
import com.hse.onlinestore.exceptions.CategoryNotFoundException;
import com.hse.onlinestore.exceptions.NotSupportedCurrencyException;
import com.hse.onlinestore.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

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
@TestPropertySource(properties = {"currency.OnlyEuro=true" })
class ProductServiceTest {

    @Autowired
    @InjectMocks
    private ProductServiceImpl productService;

    @MockBean
    private CurrencyService currencyService;

    @Autowired
    private CategoryService categoryService;

    private CategoryDto category;

    private ProductDto productDto;

    @BeforeEach
    public void setup() throws CategoryNotFoundException, NotSupportedCurrencyException {
        category = createCategory(CATEGORY_NAME, CATEGORY_DESCRIPTION);
        category = categoryService.addCategory(category);
        productDto = createProduct(CurrencyDto.EUR, PRODUCT_NAME, PRODUCT_DESCRIPTION,
                                   BigDecimal.valueOf(50), 3, 5, false, 14, BigDecimal.valueOf(5));
        productDto.setCategoryId(category.getId());
        productDto = productService.addProduct(productDto);
    }

    @Test
    void saveProductInEuroTest() throws ProductNotFoundException, CategoryNotFoundException {
        ProductDto testProductDto = productService.findProduct(productDto.getId());
        assertProduct(testProductDto, productDto);
    }

    @Test
    void saveProductInUsdTest() throws ProductNotFoundException {
        when(currencyService.getPriceInEuro(BigDecimal.valueOf(50), CurrencyDto.EUR)).thenReturn(BigDecimal.valueOf(55));
        ProductDto testProductDto = productService.findProduct(productDto.getId());
        assertProduct(testProductDto, productDto);
    }

    @Test
    void editProductTest() throws ProductNotFoundException, CategoryNotFoundException, NotSupportedCurrencyException {
        when(currencyService.getPriceInEuro(BigDecimal.valueOf(60), CurrencyDto.EUR)).thenReturn(BigDecimal.valueOf(55));

        Long productId = productDto.getId();

        CategoryDto newCategory =  createCategory(CATEGORY_NAME, CATEGORY_DESCRIPTION);
        newCategory = categoryService.addCategory(newCategory);
        ProductDto updatedProductDto = createProduct(CurrencyDto.EUR, "new Product Name",
                                                     "new product description",
                                                     BigDecimal.valueOf(60), 4, 6, true, 16, null);
        updatedProductDto.setCategoryId(newCategory.getId());
        productService.updateProduct(productId, updatedProductDto);
        ProductDto testProductDto = productService.findProduct(productId);
        assertProduct(testProductDto, updatedProductDto);
    }

    @Test
    void deleteProductTest() {
        Long productId = productDto.getId();
        productService.deleteProduct(productId);
        ProductNotFoundException thrown = assertThrows(
                ProductNotFoundException.class,
                () -> productService.findProduct(productId));

        assertTrue(thrown.getMessage().contains(("Product Not Found")));
    }

    @Test
    void listProductTest() throws CategoryNotFoundException, NotSupportedCurrencyException {
        ProductDto secondProduct = createProduct(CurrencyDto.EUR, "new Product Name",
                                                           "new product description",BigDecimal.valueOf(60),
                                                           4, 6, true, 16, null );
        secondProduct.setCategoryId(category.getId());
        productService.addProduct(secondProduct);

        List<ProductDto> allproducts = productService.findAllProducts();
        assertTrue(allproducts.size() >= 3);
    }

    private void assertProduct(ProductDto actualProduct, ProductDto expectedProduct) {
        assertEquals(expectedProduct.getName(), actualProduct.getName());
        assertEquals(expectedProduct.getDescription(), actualProduct.getDescription());
        assertEquals(expectedProduct.getPrice().setScale(2), actualProduct.getPrice());
        assertEquals(expectedProduct.getDeliveryRangeFrom(), actualProduct.getDeliveryRangeFrom());
        assertEquals(expectedProduct.getDeliveryRangeTo(), actualProduct.getDeliveryRangeTo());
        assertEquals(expectedProduct.isFreeShipping(), actualProduct.isFreeShipping());
        assertEquals(expectedProduct.getNumberOfDaysForFreeReturn(), actualProduct.getNumberOfDaysForFreeReturn());
        assertEquals(expectedProduct.getShippingPrice() == null ? null : expectedProduct.getShippingPrice().setScale(2),
                     actualProduct.getShippingPrice());
        assertEquals(expectedProduct.getCurrency(), actualProduct.getCurrency());
    }
}
