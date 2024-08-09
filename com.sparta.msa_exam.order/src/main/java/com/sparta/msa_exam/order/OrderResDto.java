package com.sparta.msa_exam.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderResDto {
    private Long order_id;
    private List<Long> order_item_id;


    public static OrderResDto from(Order order) {
        OrderResDto orderResDto = new OrderResDto();
        orderResDto.setOrder_id(order.getOrder_id());
        orderResDto.setOrder_item_id(order.getOrder_item_id());
        return orderResDto;
    }
}
