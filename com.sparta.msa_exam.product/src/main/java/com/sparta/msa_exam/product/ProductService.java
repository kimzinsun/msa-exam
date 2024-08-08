package com.sparta.msa_exam.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductReqDto productReqDto) {
        Product product = Product.createProduct(productReqDto);
        productRepository.save(product);
    }
}
