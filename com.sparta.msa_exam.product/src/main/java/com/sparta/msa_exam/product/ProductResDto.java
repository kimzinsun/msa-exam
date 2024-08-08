package com.sparta.msa_exam.product;

import lombok.Data;

@Data
public class ProductResDto {
    private Long product_id;
    private String name;
    private Integer supply_price;

    public static ProductResDto createProductResDto(Product product) {
        ProductResDto productResDto = new ProductResDto();
        productResDto.setProduct_id(product.getProduct_id());
        productResDto.setName(product.getName());
        productResDto.setSupply_price(product.getSupply_price());
        return productResDto;
    }
}
