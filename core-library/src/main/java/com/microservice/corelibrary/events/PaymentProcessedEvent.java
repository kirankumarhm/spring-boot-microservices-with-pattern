package com.microservice.corelibrary.events;

import com.microservice.corelibrary.model.PaymentDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProcessedEvent {
    private String orderId;
    private String paymentId;
//    private PaymentDetails paymentDetails;
}
