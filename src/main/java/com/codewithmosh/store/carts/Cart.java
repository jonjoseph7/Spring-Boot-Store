package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 16)
    private UUID id;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDate createdDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItem> items = new HashSet<>();

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CartItem getItem(Long productId) {
        return items.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addProduct(Product product) {
        var cartItem = getItem(product.getId());
        if (cartItem == null) {
            cartItem = new CartItem(this, product);
            items.add(cartItem);
        }
        else {
            cartItem.incrementQuantity();
        }
        return cartItem;
    }

    public void removeItem(Long productId) {
        var cartItem = getItem(productId);
        if (cartItem != null) {
            items.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void removeAllItems() {
        items.forEach(i -> i.setCart(null));
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }


}