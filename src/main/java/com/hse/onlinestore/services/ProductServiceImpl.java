package com.hse.onlinestore.services;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import com.hse.onlinestore.dtos.CurrencyDto;
import com.hse.onlinestore.dtos.ProductDto;
import com.hse.onlinestore.exceptions.CategoryNotFoundException;
import com.hse.onlinestore.exceptions.NotSupportedCurrencyException;
import com.hse.onlinestore.exceptions.ProductNotFoundException;
import com.hse.onlinestore.mappers.ProductMapper;
import com.hse.onlinestore.model.Category;
import com.hse.onlinestore.model.Product;
import com.hse.onlinestore.repositories.CategoryRepository;
import com.hse.onlinestore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    @Value("${currency.OnlyEuro}")
    private boolean onlyEuro;

    private final CurrencyService currencyService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository, ProductMapper productMapper,
                              CurrencyService currencyService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
        this.currencyService = currencyService;
    }

    @Override public ProductDto addProduct(ProductDto product) throws CategoryNotFoundException, NotSupportedCurrencyException {
        BigDecimal priceInEuro = getProductPriceInEuro(product.getPrice(), product.getCurrency());

        Category category = categoryRepository.findById(product.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException("Category Not Found"));

        Product modelProduct = productMapper.mapFromDtoToModel(product);
        modelProduct.setPriceInEuro(priceInEuro);
        modelProduct.setCategory(category);

        Product result = productRepository.save(modelProduct);
        product.setId(result.getId());
        return product;
    }

    @Override
    public ProductDto findProduct(Long productId) throws ProductNotFoundException {
        return productMapper.mapFromModelToDto(
                productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product Not Found")));
    }

    @Override public ProductDto updateProduct(Long productId,
                                              ProductDto updatedProduct) throws ProductNotFoundException, CategoryNotFoundException, NotSupportedCurrencyException {
        BigDecimal priceInEuro = getProductPriceInEuro(updatedProduct.getPrice(), updatedProduct.getCurrency());
        Category updatedCategory = categoryRepository.findById(updatedProduct.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException("Category Not Found"));
        Product originalProduct = productMapper.mapFromDtoToModel(updatedProduct);
        originalProduct.setId(productId);
        originalProduct.setPriceInEuro(priceInEuro);
        originalProduct.setCategory(updatedCategory);
        productRepository.save(originalProduct);
        return updatedProduct;
    }

    @Override public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override public List<ProductDto> findAllProducts() {
        return productMapper.mapFromModelListToDtoList(productRepository.findAll());
    }

    private BigDecimal getProductPriceInEuro(BigDecimal price, CurrencyDto currency) throws NotSupportedCurrencyException {
        if (!CurrencyDto.EUR.equals(currency)) {
            if (onlyEuro) {
                throw new NotSupportedCurrencyException("Currency not supported");
            }
            return currencyService.getPriceInEuro(price, currency);
        }
        return price;
    }
}
