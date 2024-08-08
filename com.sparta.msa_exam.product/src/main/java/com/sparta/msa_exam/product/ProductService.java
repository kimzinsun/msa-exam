package com.sparta.msa_exam.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Page<Product> getProducts(Pageable pageble) {
        log.info(pageble.toString());
        return productRepository.findAll(pageble);
    }
}
