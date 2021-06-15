package com.hse.onlinestore.mappers;

import java.util.List;

import com.hse.onlinestore.dtos.CategoryDto;
import com.hse.onlinestore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    List<CategoryDto> mapFromModelListToDtoList(Iterable<Category> modelCategories);

    CategoryDto mapFromModelToDto(Category category);

    @Mapping(target = "products", ignore = true)
    Category mapFromDtoToModel(CategoryDto categoryDto);
}
