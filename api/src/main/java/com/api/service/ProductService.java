package com.api.service;

import com.api.dto.ProductDTO;
import com.api.dto.ProductIndexDTO;
import com.api.dto.ProductStockDTO;
import com.api.model.Product;

import java.text.ParseException;
import java.util.List;

public interface ProductService {

    Product dtoToEntity(ProductDTO productDTO);

    List<ProductDTO> findAll();

    Product get(Long id);

    Product save(ProductDTO productDTO);

    //Product update(ProductDTO productDTO);

    void delete(Long id);

    List<ProductIndexDTO> index(ProductIndexDTO productIndexDTO);

    ProductStockDTO getStock(ProductStockDTO productStockDTO) throws ParseException;
}
