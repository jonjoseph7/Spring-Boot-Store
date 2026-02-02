package com.jonathanjoseph.store.carts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductItemDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
