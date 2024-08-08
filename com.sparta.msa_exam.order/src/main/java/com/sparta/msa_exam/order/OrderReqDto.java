package com.sparta.msa_exam.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderReqDto {
    private String name;
    private List<Long> product_id;
}
