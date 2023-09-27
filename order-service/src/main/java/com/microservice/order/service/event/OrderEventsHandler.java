package com.microservice.order.service.event;

import com.microservice.order.service.model.Order;
import com.microservice.order.service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventsHandler {
    private final OrderRepository orderRepository;


    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent){
        Order order = new Order();
        BeanUtils.copyProperties(orderCreatedEvent, order);
        orderRepository.saveAndFlush(order);
    }

    @EventHandler
    public void on(OrderApprovedEvent orderApprovedEvent){
        Order order = orderRepository.findByOrderId(orderApprovedEvent.getOrderId());

        if( order == null){
            return;
        }
        order.setOrderStatus(orderApprovedEvent.getOrderStatus());
        orderRepository.saveAndFlush(order);
    }

    @EventHandler
    public void on(OrderRejectedEvent orderRejectedEvent){
        Order order = orderRepository.findByOrderId(orderRejectedEvent.getOrderId());

        if( order == null){
            return;
        }
        order.setOrderStatus(orderRejectedEvent.getOrderStatus());
        orderRepository.saveAndFlush(order);
    }
}
