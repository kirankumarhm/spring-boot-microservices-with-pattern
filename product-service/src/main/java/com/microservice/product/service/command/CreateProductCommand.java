package com.microservice.product.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
