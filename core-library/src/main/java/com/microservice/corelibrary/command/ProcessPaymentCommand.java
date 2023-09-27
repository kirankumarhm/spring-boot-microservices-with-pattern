package com.microservice.corelibrary.command;

import com.microservice.corelibrary.model.PaymentDetails;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ProcessPaymentCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private PaymentDetails paymentDetails;

}
