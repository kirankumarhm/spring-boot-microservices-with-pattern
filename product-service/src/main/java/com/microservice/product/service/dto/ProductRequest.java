package com.microservice.product.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Product title can not be blank")
    private String title;

    @Min(value = 1, message = "Price cannot be less than 1")
    private BigDecimal price;

    @Min(value = 1, message = "Quantity cannot be less than 1")
    private Integer quantity;
}
