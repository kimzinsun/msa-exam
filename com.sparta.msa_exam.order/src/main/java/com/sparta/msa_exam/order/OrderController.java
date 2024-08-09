package com.sparta.msa_exam.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody OrderReqDto orderReqDto,
                                         @RequestHeader(value = "X-User-Id", required = true) String userId) {
        return orderService.createOrder(orderReqDto, userId);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        try {
            OrderResDto orderResDto = orderService.getOrderById(orderId);
            return ResponseEntity.status(200).body(orderResDto);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("주문이 존재하지 않습니다.");
        }
    }


}
