package com.microservice.order.service.event;

import com.microservice.order.service.model.OrderStatus;
import lombok.Value;

@Value
public class OrderRejectedEvent {
    private String orderId;
    private String reason;
    private OrderStatus orderStatus = OrderStatus.REJECTED;
}
