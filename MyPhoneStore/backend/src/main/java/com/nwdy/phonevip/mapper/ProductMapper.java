package com.nwdy.phonevip.mapper;

import com.nwdy.phonevip.dto.request.ProductRequest;
import com.nwdy.phonevip.dto.response.ProductDetailResponse;
import com.nwdy.phonevip.dto.response.ProductResponse;
import com.nwdy.phonevip.dto.response.ProductSearchResponse;
import com.nwdy.phonevip.model.Product;
import com.nwdy.phonevip.model.ProductDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductRequest productRequest);

    ProductResponse toProductResponse(Product product);

    ProductDetailResponse toProductDetailResponse(Product product);

    ProductDocument toProductDocument(Product product);

    @Mapping(source = "id", target = "id")
    ProductSearchResponse toProductSearchResponse(ProductDocument productDocument);

    @Mapping(target = "id", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductRequest productRequest);
}
