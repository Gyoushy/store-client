package com.hse.onlinestore.services;

import java.util.List;

import com.hse.onlinestore.dtos.CategoryDto;
import com.hse.onlinestore.exceptions.CategoryNotFoundException;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto category);

    List<CategoryDto> listCategories();

    CategoryDto getCategory(int categoryId) throws CategoryNotFoundException;

    CategoryDto updateCategory(int categoryId, CategoryDto category) throws CategoryNotFoundException;

    void deleteCategory(int categoryId);
}
