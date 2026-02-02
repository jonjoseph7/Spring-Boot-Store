package com.jonathanjoseph.store.orders;

import com.jonathanjoseph.store.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuthService authService;

    public List<OrderDto> getOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getAllByCustomer(user);

        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    public OrderDto getOrder(Long orderId) {
        var user = authService.getCurrentUser();
        var order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new OrderNotFoundException();
        }
        if (!order.belongsToUser(user)) {
            throw new OrderNotAllowedException();
        }
        return orderMapper.toDto(order);
    }



}
