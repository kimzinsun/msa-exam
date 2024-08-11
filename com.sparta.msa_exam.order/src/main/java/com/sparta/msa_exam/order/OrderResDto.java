package com.sparta.msa_exam.order;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResDto implements Serializable {
    private Long order_id;
    private List<Long> order_item_id;


    public static OrderResDto from(Order order) {
        OrderResDto orderResDto = new OrderResDto();
        orderResDto.setOrder_id(order.getOrder_id());

        List<Long> orderItemIds = new ArrayList<>(order.getOrder_item_id());
        orderResDto.setOrder_item_id(orderItemIds);

        return orderResDto;
    }

}
