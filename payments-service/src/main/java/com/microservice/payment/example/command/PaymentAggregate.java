package com.microservice.payment.example.command;

import com.microservice.corelibrary.command.ProcessPaymentCommand;
import com.microservice.corelibrary.events.PaymentProcessedEvent;
import com.microservice.corelibrary.model.PaymentDetails;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private PaymentDetails paymentDetails;

    public PaymentAggregate(){

    }

    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand processPaymentCommand){
        if(processPaymentCommand.getPaymentDetails() == null) {
            throw new IllegalArgumentException("Missing payment details");
        }

        if(processPaymentCommand.getPaymentId() == null || processPaymentCommand.getPaymentId().isBlank()){
            throw new IllegalArgumentException("Payment Id can not be empty ot null");
        }
        if(processPaymentCommand.getOrderId()  == null || processPaymentCommand.getOrderId().isBlank()){
            throw new IllegalArgumentException("Order Id can not be empty ot null");
        }

        PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
                .paymentId(processPaymentCommand.getPaymentId())
                .orderId(processPaymentCommand.getOrderId())
//                .paymentDetails(processPaymentCommand.getPaymentDetails())
                .build();

        AggregateLifecycle.apply(paymentProcessedEvent);

    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent){
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
//        this.paymentDetails = paymentProcessedEvent.getPaymentDetails();
    }

}
