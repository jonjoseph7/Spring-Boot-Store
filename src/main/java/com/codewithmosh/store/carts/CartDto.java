package com.codewithmosh.store.carts;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Data
public class CartDto {
    private UUID id;
    private Set<CartItemDto> items = new HashSet<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
