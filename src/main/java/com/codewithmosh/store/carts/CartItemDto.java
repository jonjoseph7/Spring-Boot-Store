package com.codewithmosh.store.carts;

import com.codewithmosh.store.carts.ProductItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private ProductItemDto product;
    private Integer quantity;
    private BigDecimal totalPrice;

}
