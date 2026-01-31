package com.codewithmosh.store.orders;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private PaymentStatus status;
    private Date createdAt;
    private Set<OrderItemDto> orderItems;
    private BigDecimal totalPrice;
}
