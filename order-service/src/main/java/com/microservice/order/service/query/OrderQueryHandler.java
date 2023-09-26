package com.microservice.order.service.query;

import com.microservice.order.service.dto.OrderResponse;
import com.microservice.order.service.model.Order;
import com.microservice.order.service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderQueryHandler {

    private final OrderRepository orderRepository;

    @QueryHandler
    public List<OrderResponse> findOrders(FindOrderQuery orderQuery){
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(order ->
                        OrderResponse.builder()
                                .orderStatus(order.getOrderStatus())
                                .quantity(order.getQuantity())
                                .productId(order.getProductId())
                                .orderId(order.orderId)
                                .addressId(order.getAddressId())
                                .userId(order.getUserId())
                                .build()
                ).collect(Collectors.toList());
    }
}
