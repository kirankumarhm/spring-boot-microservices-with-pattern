package com.microservice.order.service.saga;

import com.microservice.corelibrary.command.ProcessPaymentCommand;
import com.microservice.corelibrary.command.ReserveProductCommand;
import com.microservice.corelibrary.events.PaymentProcessedEvent;
import com.microservice.corelibrary.events.ProductReservedEvent;
import com.microservice.corelibrary.model.User;
import com.microservice.corelibrary.query.FetchUserPaymentDetailsQuery;
import com.microservice.order.service.command.ApproveOrderCommand;
import com.microservice.order.service.event.OrderApprovedEvent;
import com.microservice.order.service.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Saga
@Slf4j
//@RequiredArgsConstructor
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;


    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent){

        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .productId(orderCreatedEvent.getProductId())
                .orderId(orderCreatedEvent.getOrderId())
                .userId(orderCreatedEvent.getUserId())
                .quantity(orderCreatedEvent.getQuantity())
                .build();

        log.info("OrdercreatedEvent handled for orderId {} and productId {}",
                reserveProductCommand.getOrderId(), reserveProductCommand.getProductId());

        commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {
            @Override
            public void onResult(@Nonnull CommandMessage<? extends ReserveProductCommand> commandMessage,
                                 @Nonnull CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()){
                    //Start a compensating transaction
                }
            }
        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent){
        log.info("ProductReservedEvent handled for orderId {} and productId {}",
                productReservedEvent.getOrderId(), productReservedEvent.getProductId());

        FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery =
                new FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());
        User userPaymentDetails = null;

        try {
            userPaymentDetails = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }

        if(userPaymentDetails == null){
            return;
        }
        log.info("Successfully fetched user payment details for the user :{}",
                userPaymentDetails.getFirstName());

        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                .orderId(productReservedEvent.getOrderId())
                .paymentDetails(userPaymentDetails.getPaymentDetails())
                .paymentId(UUID.randomUUID().toString())
                .build();

        String result = null;
        try {
             result = commandGateway.sendAndWait(processPaymentCommand, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Error occurred while processing payment : {}",e.getMessage());
        }

        if(result == null){
            log.info("The ProcessPaymentCommand resulted null. Initiating compensating transaction.");
        }

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent paymentProcessedEvent){

        ApproveOrderCommand approveOrderCommand = new ApproveOrderCommand(paymentProcessedEvent.getOrderId());
        commandGateway.send(approveOrderCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent){

        log.info("Order is approved. Order Saga is complete for the order id {}",
                orderApprovedEvent.getOrderId());

//        SagaLifecycle.end();

    }

}
