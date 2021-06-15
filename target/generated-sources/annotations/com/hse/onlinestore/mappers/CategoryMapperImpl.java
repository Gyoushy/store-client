package com.hse.onlinestore.mappers;

import com.hse.onlinestore.dtos.CategoryDto;
import com.hse.onlinestore.dtos.CurrencyDto;
import com.hse.onlinestore.dtos.ProductDto;
import com.hse.onlinestore.model.Category;
import com.hse.onlinestore.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-15T12:05:26+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public List<CategoryDto> mapFromModelListToDtoList(Iterable<Category> modelCategories) {
        if ( modelCategories == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>();
        for ( Category category : modelCategories ) {
            list.add( mapFromModelToDto( category ) );
        }

        return list;
    }

    @Override
    public CategoryDto mapFromModelToDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId( category.getId() );
        categoryDto.setName( category.getName() );
        categoryDto.setDescription( category.getDescription() );
        categoryDto.setProducts( productListToProductDtoList( category.getProducts() ) );

        return categoryDto;
    }

    @Override
    public Category mapFromDtoToModel(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryDto.getId() );
        category.setName( categoryDto.getName() );
        category.setDescription( categoryDto.getDescription() );

        return category;
    }

    protected ProductDto productToProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setId( product.getId() );
        productDto.setName( product.getName() );
        productDto.setDescription( product.getDescription() );
        productDto.setPriceInEuro( product.getPriceInEuro() );
        productDto.setPrice( product.getPrice() );
        if ( product.getCurrency() != null ) {
            productDto.setCurrency( Enum.valueOf( CurrencyDto.class, product.getCurrency() ) );
        }
        productDto.setNumberOfDaysForFreeReturn( product.getNumberOfDaysForFreeReturn() );
        productDto.setFreeShipping( product.isFreeShipping() );
        productDto.setShippingPrice( product.getShippingPrice() );
        productDto.setDeliveryRangeFrom( product.getDeliveryRangeFrom() );
        productDto.setDeliveryRangeTo( product.getDeliveryRangeTo() );

        return productDto;
    }

    protected List<ProductDto> productListToProductDtoList(List<Product> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductDto> list1 = new ArrayList<ProductDto>( list.size() );
        for ( Product product : list ) {
            list1.add( productToProductDto( product ) );
        }

        return list1;
    }
}
