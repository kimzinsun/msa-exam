package com.sparta.msa_exam.order;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;
    private String name;
    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "order_item_id")
    private List<Long> order_item_id;


    public static Order createOrder(OrderReqDto orderReqDto, String userId) {
        Order order = new Order();
        order.setName(userId);
        order.setOrder_item_id(orderReqDto.getProduct_id());
        return order;

    }

    public void addProducts(List<Long> productId) {
        this.order_item_id.addAll(productId);
    }
}
