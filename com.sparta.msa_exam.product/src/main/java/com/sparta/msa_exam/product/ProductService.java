package com.sparta.msa_exam.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @CachePut(cacheNames = "productCache", key = "#productReqDto.name", unless = "#result == null")
    @CacheEvict(cacheNames = "productAllCache", allEntries = true)
    public void createProduct(ProductReqDto productReqDto) {
        try {
            Product product = Product.createProduct(productReqDto);
            productRepository.save(product);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("상품 등록에 실패했습니다.");
        }
    }

    @Cacheable(cacheNames = "productAllCache", key = "#pageble.pageNumber")
    public Page<Product> getProducts(Pageable pageble) {
        log.info(pageble.toString());
        return productRepository.findAll(pageble);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
}
