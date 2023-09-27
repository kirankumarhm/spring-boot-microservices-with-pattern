package com.microservice.order.service.command;

import com.microservice.order.service.event.OrderApprovedEvent;
import com.microservice.order.service.event.OrderCreatedEvent;
import com.microservice.order.service.event.OrderRejectedEvent;
import com.microservice.order.service.model.OrderStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    public String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);

        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) throws Exception {
        this.orderId = orderCreatedEvent.getOrderId();
        this.productId = orderCreatedEvent.getProductId();
        this.userId = orderCreatedEvent.getUserId();
        this.addressId = orderCreatedEvent.getAddressId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
    }

    @CommandHandler
    public void handle(ApproveOrderCommand approveOrderCommand){
        OrderApprovedEvent orderApprovedEvent =
                new OrderApprovedEvent(approveOrderCommand.getOrderId());
        AggregateLifecycle.apply(orderApprovedEvent);
    }

    @CommandHandler
    public void handle(RejectOrderCommand rejectOrderCommand){
        OrderRejectedEvent orderRejectedEvent =
                new OrderRejectedEvent(rejectOrderCommand.getOrderId(), rejectOrderCommand.getReason());
        AggregateLifecycle.apply(orderRejectedEvent);
    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent orderApprovedEvent){
        this.orderStatus = orderApprovedEvent.getOrderStatus();
    }

    @EventSourcingHandler
    public void on(OrderRejectedEvent orderRejectedEvent){
        this.orderStatus = orderRejectedEvent.getOrderStatus();
    }
}
