package com.jonathanjoseph.store.orders;

public class OrderNotAllowedException extends RuntimeException {
    public OrderNotAllowedException() {
        super("You do not have access to this order");
    }
}
