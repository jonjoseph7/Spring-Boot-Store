package com.codewithmosh.store.orders;

import com.codewithmosh.store.orders.ProductItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OrderItemDto {
    private ProductItemDto product;
    private Integer quantity;
    private BigDecimal totalPrice;;
}
