package com.hse.onlinestore.mappers;

import java.util.List;

import com.hse.onlinestore.dtos.ProductDto;
import com.hse.onlinestore.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = {MapperUtils.class})
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    List<ProductDto> mapFromModelListToDtoList(Iterable<Product> modelProducts);

    List<Product> mapFromDtoListToModelList(List<ProductDto> productDtoList);

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto mapFromModelToDto(Product product);

    @Mapping(target = "category", ignore = true)
    @Mapping(source = "currency", target = "currency", qualifiedByName = {"MapperUtils", "currencyToString"})
    Product mapFromDtoToModel(ProductDto productDto);
}
