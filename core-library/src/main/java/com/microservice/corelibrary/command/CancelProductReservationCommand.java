package com.microservice.corelibrary.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelProductReservationCommand {
    @TargetAggregateIdentifier
    private String productId;
    private int quantity;
    private String orderId;
    private String userId;
    private String reason;
}
