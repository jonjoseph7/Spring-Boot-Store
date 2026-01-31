package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    public CartItem(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
        this.quantity = 1;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}