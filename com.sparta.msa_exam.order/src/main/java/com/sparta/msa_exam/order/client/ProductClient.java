package com.sparta.msa_exam.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "products-service")
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductResponseDto getProduct(Long id);
}
