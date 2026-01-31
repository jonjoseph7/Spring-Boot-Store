package com.codewithmosh.store.carts;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeQuantityRequest {
    @NotNull(message = "Must enter quantity")
    @Min(value = 1, message = "Must be greater than 0")
    @Max(value = 99, message = "Must be less than 100")
    private Integer quantity;
}
