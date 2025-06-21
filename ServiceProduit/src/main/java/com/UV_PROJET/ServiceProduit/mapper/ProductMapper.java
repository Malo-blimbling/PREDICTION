package com.UV_PROJET.ServiceProduit.mapper;

import com.UV_PROJET.ServiceProduit.model.Product;
import com.UV_PROJET.ServiceProduit.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", source = "id")
    ProductDTO toDTO(Product product);

    @Mapping(target = "id", source = "id")
    Product toEntity(ProductDTO productDTO);
}
