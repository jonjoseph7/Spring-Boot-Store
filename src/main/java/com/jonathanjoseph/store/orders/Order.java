package com.jonathanjoseph.store.orders;

import com.jonathanjoseph.store.carts.Cart;
import com.jonathanjoseph.store.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ToString.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    public Order(User user, Cart cart) {
        this.customer = user;
        this.createdAt = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
        this.totalPrice = cart.getTotalPrice();

        cart.getItems().forEach(c -> {
            this.orderItems.add(new OrderItem(this, c.getProduct(), c.getQuantity()));
        });
    }

    public boolean belongsToUser(User user) {
        return this.customer.equals(user);
    }

}