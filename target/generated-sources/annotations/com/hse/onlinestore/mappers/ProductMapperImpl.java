package com.hse.onlinestore.mappers;

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
    date = "2021-06-15T10:28:29+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public List<ProductDto> mapFromModelListToDtoList(Iterable<Product> modelProducts) {
        if ( modelProducts == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>();
        for ( Product product : modelProducts ) {
            list.add( mapFromModelToDto( product ) );
        }

        return list;
    }

    @Override
    public List<Product> mapFromDtoListToModelList(List<ProductDto> productDtoList) {
        if ( productDtoList == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( productDtoList.size() );
        for ( ProductDto productDto : productDtoList ) {
            list.add( mapFromDtoToModel( productDto ) );
        }

        return list;
    }

    @Override
    public ProductDto mapFromModelToDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setCategoryId( productCategoryId( product ) );
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

    @Override
    public Product mapFromDtoToModel(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setCurrency( MapperUtils.currencyToString( productDto.getCurrency() ) );
        if ( productDto.getId() != null ) {
            product.setId( productDto.getId() );
        }
        product.setName( productDto.getName() );
        product.setDescription( productDto.getDescription() );
        product.setPrice( productDto.getPrice() );
        product.setPriceInEuro( productDto.getPriceInEuro() );
        product.setNumberOfDaysForFreeReturn( productDto.getNumberOfDaysForFreeReturn() );
        product.setFreeShipping( productDto.isFreeShipping() );
        product.setShippingPrice( productDto.getShippingPrice() );
        product.setDeliveryRangeFrom( productDto.getDeliveryRangeFrom() );
        product.setDeliveryRangeTo( productDto.getDeliveryRangeTo() );

        return product;
    }

    private int productCategoryId(Product product) {
        if ( product == null ) {
            return 0;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return 0;
        }
        int id = category.getId();
        return id;
    }
}
