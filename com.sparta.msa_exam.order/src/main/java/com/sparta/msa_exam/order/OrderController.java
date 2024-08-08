package com.sparta.msa_exam.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Value("${server.port}")
    private String port;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody OrderReqDto orderReqDto,
                                         @RequestHeader(value = "X-User-Id", required = true) String userId) {
        return orderService.createOrder(orderReqDto, userId);
    }


}
