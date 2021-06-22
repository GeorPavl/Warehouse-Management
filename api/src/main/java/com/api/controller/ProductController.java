package com.api.controller;

import com.api.dto.ProductDTO;
import com.api.dto.ProductIndexDTO;
import com.api.dto.ProductStockDTO;
import com.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/pr")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<ProductDTO> getProductsList(){
        return productService.findAll();
    }

    @GetMapping("/get")
    public ProductDTO getProduct(@RequestParam Long id){
        return new ProductDTO(productService.get(id));
    }

    @PostMapping("/save")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO) {
        return new ProductDTO(productService.save(productDTO));
    }

    @DeleteMapping("/delete")
    public void deleteProduct(@RequestParam Long id){
        productService.delete(id);
    }

    @PostMapping("/index")
    public List<ProductIndexDTO> indexProduct(@RequestBody ProductIndexDTO productIndexDTO){
        return productService.index(productIndexDTO);
    }

    @PostMapping("/stock")
    public ProductStockDTO getProductStock(@RequestBody ProductStockDTO productStockDTO) throws ParseException {
        return productService.getStock(productStockDTO);
    }
}
