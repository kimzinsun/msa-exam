package com.sparta.msa_exam.order;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRespository orderRespository;

    public OrderService(OrderRespository orderRespository) {
        this.orderRespository = orderRespository;
    }

    public ResponseEntity<?> createOrder(OrderReqDto orderReqDto, String userId) {
        Order order = Order.createOrder(orderReqDto, userId);
        orderRespository.save(order);
        return ResponseEntity.ok().build();
    }
}
