package com.sparta.msa_exam.product;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity(name = "products")
@Table
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;
    private String name;
    private Integer supply_price;

    public static Product createProduct(ProductReqDto productReqDto) {
        Product product = new Product();
        product.setName(productReqDto.getName());
        product.setSupply_price(productReqDto.getSupply_price());
        return product;
    }

}
