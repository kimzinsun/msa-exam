package com.sparta.msa_exam.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    @Value("${server.port}")
    private String port;
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody ProductReqDto productReqDto,
                                           @RequestHeader(value = "X-Role", required = true) String role) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("ServerPort", port);

        if(!"MANAGER".equals(role)) {
            return ResponseEntity.status(403).headers(headers).body("매니저 권한이 없습니다.");
        }
        productService.createProduct(productReqDto);

        return ResponseEntity.status(201).headers(headers).body("상품이 등록되었습니다.");
    }

    @GetMapping("")
    public ResponseEntity<?> getProducts(Pageable pageable) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("ServerPort", port);
        return ResponseEntity.status(200).headers(headers).body(productService.getProducts(pageable));
    }


}
