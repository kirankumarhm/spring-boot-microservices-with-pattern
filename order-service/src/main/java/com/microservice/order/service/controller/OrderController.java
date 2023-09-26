package com.microservice.order.service.controller;

import com.microservice.order.service.command.CreateOrderCommand;
import com.microservice.order.service.dto.OrderRequest;
import com.microservice.order.service.dto.OrderResponse;
import com.microservice.order.service.model.OrderStatus;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.microservice.order.service.query.FindOrderQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final CommandGateway commandGateway;

    private final QueryGateway queryGateway;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @CircuitBreaker(name = "inventory", fallbackMethod = "placeOrderFallbackMethod")
    public String placeOrder(@RequestBody OrderRequest orderRequest){

        String userId = "27b95829-4f3f-4ddf-8983-151ba010e35b";
        String orderId = UUID.randomUUID().toString();

        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .addressId(orderRequest.getAddressId())
                .productId(orderRequest.getProductId())
                .userId(userId)
                .quantity(orderRequest.getQuantity())
                .orderId(orderId)
                .orderStatus(OrderStatus.CREATED)
                .build();

        String result = commandGateway.sendAndWait(createOrderCommand);

        return result;
    }

    public String placeOrderFallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return "Oops!!! Something went wrong, please try later";
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders(){
        FindOrderQuery orderQuery = new FindOrderQuery();
        List<OrderResponse> orderResponseList = queryGateway.query(orderQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
        return orderResponseList;
    }

}
