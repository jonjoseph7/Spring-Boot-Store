package com.jonathanjoseph.store.payments;

import com.jonathanjoseph.store.orders.Order;
import com.jonathanjoseph.store.carts.CartEmptyException;
import com.jonathanjoseph.store.carts.CartNotFoundException;
import com.jonathanjoseph.store.carts.CartRepository;
import com.jonathanjoseph.store.orders.OrderRepository;
import com.jonathanjoseph.store.auth.AuthService;
import com.jonathanjoseph.store.carts.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;
    private final PaymentGateway paymentGateway;




    public CheckoutResponse checkout(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        var user = authService.getCurrentUser();

        var order = new Order(user, cart);
        orderRepository.save(order);

        try {
            var session = paymentGateway.createCheckoutSession(order);
            cartService.clearCart(cartId);
            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        }
        catch (PaymentException e) {
            orderRepository.delete(order);
            throw e;
        }
    }

    public void handleWebhookEvent(WebhookRequest request) {
        paymentGateway
                .parseWebhookRequest(request)
                .ifPresent(paymentResult -> {
                    var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setStatus(paymentResult.getPaymentStatus());
                    orderRepository.save(order);
                });
    }

}
