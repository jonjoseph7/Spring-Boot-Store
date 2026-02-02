package com.jonathanjoseph.store.orders;

import com.jonathanjoseph.store.common.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrderController {
    private final OrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(
            @PathVariable() Long orderId
    ) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @ExceptionHandler(OrderNotAllowedException.class)
    public ResponseEntity<ErrorDto> handleExceptionsNotAllowed(Exception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleExceptionsNotFound(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage()));
    }
}
