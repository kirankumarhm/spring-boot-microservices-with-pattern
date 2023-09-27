package com.microservice.order.service.event;

import com.microservice.order.service.model.OrderStatus;
import lombok.*;

@Value
public class OrderApprovedEvent {
    private String orderId;
    private OrderStatus orderStatus = OrderStatus.APPROVED;
}
