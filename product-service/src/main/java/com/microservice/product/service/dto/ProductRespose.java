package com.microservice.product.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRespose {
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
