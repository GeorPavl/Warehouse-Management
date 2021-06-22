package com.api.repository;

import com.api.dto.ProductIndexDTO;

import java.util.List;

public interface ProductIndexRepository {

    List<ProductIndexDTO> productQuery(ProductIndexDTO search);
}
