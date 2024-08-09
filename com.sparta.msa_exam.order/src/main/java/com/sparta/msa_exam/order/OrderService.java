package com.sparta.msa_exam.order;

import com.sparta.msa_exam.order.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    private final OrderRespository orderRespository;
    private final ProductClient productClient;

    public OrderService(OrderRespository orderRespository, ProductClient productClient) {
        this.orderRespository = orderRespository;
        this.productClient = productClient;
    }

    public ResponseEntity<?> createOrder(OrderReqDto orderReqDto, String userId) {
        try {
            for (Long productId : orderReqDto.getProduct_id()) {
                productClient.getProduct(productId);
            }
            Order order = Order.createOrder(orderReqDto, userId);
            Order savedOrder = orderRespository.save(order);
            return ResponseEntity.status(200).body(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("존재하지 않는 상품입니다.");
        }
    }

    public OrderResDto getOrderById(Long orderId) {
        try {
            Order order = orderRespository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
            return OrderResDto.from(order);
        } catch (Exception e) {
            return null;
        }
    }
}
