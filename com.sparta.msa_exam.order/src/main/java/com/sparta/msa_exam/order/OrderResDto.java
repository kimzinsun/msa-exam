package com.sparta.msa_exam.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderResDto {
    private Long order_id;
    private String name;
    private List<Long> order_item_id;


}
