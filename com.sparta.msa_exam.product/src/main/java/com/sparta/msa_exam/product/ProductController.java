package com.sparta.msa_exam.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody ProductReqDto productReqDto,
                                           @RequestHeader(value = "X-Role", required = true) String role) {
        if (!"MANAGER".equals(role)) {
            return createResponse("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        try {
            productService.createProduct(productReqDto);
            return createResponse("상품이 등록되었습니다.", HttpStatus.CREATED);
        } catch (Exception e) {
            return createResponse("상품 등록에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getProducts(Pageable pageable) {
        return ResponseEntity.status(200).body(productService.getProducts(pageable));
    }

    private ResponseEntity<?> createResponse(String message, HttpStatus status) {

        return ResponseEntity.status(status).body(message);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        try {
            return ResponseEntity.status(200).body(productService.getProductById(productId));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("상품이 존재하지 않습니다.");
        }
    }

}
