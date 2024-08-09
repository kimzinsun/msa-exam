package com.sparta.msa_exam.order;

import com.sparta.msa_exam.order.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
            validationProduct(orderReqDto.getProduct_id());
            Order order = Order.createOrder(orderReqDto, userId);
            Order savedOrder = orderRespository.save(order);
            return ResponseEntity.status(200).body(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("존재하지 않는 상품입니다.");
        }
    }

    public OrderResDto getOrderById(Long orderId) {
        Order order = getOrder(orderId);
        return OrderResDto.from(order);
    }

    public ResponseEntity<?> updateOrder(Long orderId, OrderReqDto orderReqDto) {
        try {
            Order order = getOrder(orderId);
            validationProduct(orderReqDto.getProduct_id());
            order.addProducts(orderReqDto.getProduct_id());
            Order savedOrder = orderRespository.save(order);
            return ResponseEntity.status(200).body(savedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("존재하지 않는 주문입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("존재하지 않는 상품입니다.");
        }
    }

    public Order getOrder(Long orderId) {
        return orderRespository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
    }

    private void validationProduct(List<Long> productIds) {
        for (Long productId : productIds) {
            productClient.getProduct(productId);
        }
    }

}
