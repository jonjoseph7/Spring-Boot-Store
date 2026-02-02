package com.jonathanjoseph.store.payments;

import com.jonathanjoseph.store.orders.Order;

import java.util.Optional;


public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
