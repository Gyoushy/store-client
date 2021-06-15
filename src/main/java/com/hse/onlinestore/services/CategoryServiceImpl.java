package com.hse.onlinestore.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.hse.onlinestore.dtos.CategoryDto;
import com.hse.onlinestore.dtos.CurrencyDto;
import com.hse.onlinestore.dtos.ProductDto;
import com.hse.onlinestore.exceptions.CategoryNotFoundException;
import com.hse.onlinestore.mappers.CategoryMapper;
import com.hse.onlinestore.mappers.ProductMapper;
import com.hse.onlinestore.model.Category;
import com.hse.onlinestore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final ProductMapper productMapper;

    private final CurrencyService currencyService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
                               ProductMapper productMapper, CurrencyService currencyService) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
        this.currencyService = currencyService;
    }

    @Override
    public CategoryDto addCategory(CategoryDto category) {
        Category model = categoryMapper.mapFromDtoToModel(category);
        Category finalModel = model;
        addProductsToCategory(category, finalModel);
        finalModel = categoryRepository.save(finalModel);
        category.setId(finalModel.getId());
        return category;
    }

    @Override
    public List<CategoryDto> listCategories() {
        return categoryMapper.mapFromModelListToDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryDto getCategory(int categoryId) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException("Category Not Found"));
        List<ProductDto> products = productMapper.mapFromModelListToDtoList(category.getProducts());
        CategoryDto categoryDto = categoryMapper.mapFromModelToDto(category);
        categoryDto.setProducts(products);
        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(int categoryId, CategoryDto category) throws CategoryNotFoundException {
        Category model = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException("Category Not Found"));
        model.setName(category.getName());
        model.setDescription(category.getDescription());
        categoryRepository.save(model);
        return category;
    }

    @Override public void deleteCategory(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    private void addProductsToCategory(CategoryDto category, Category finalModel) {
        List<ProductDto> updatedProducts = category.getProducts().stream().filter(p -> !CurrencyDto.EUR.equals(p.getCurrency())).
                map(p -> {
                    p.setPriceInEuro(currencyService.getPriceInEuro(p.getPrice(), p.getCurrency()));
                    return p;
                }).collect(
                Collectors.toList());
        productMapper.mapFromDtoListToModelList(updatedProducts).forEach(
                finalModel::addProduct);
    }
}
