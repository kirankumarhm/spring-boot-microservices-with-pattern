package com.microservice.product.service.events;

import com.microservice.corelibrary.events.ProductReservationCancelledEvent;
import com.microservice.corelibrary.events.ProductReservedEvent;
import com.microservice.product.service.dto.ProductRespose;
import com.microservice.product.service.events.ProductCreatedEvent;
import com.microservice.product.service.model.Product;
import com.microservice.product.service.query.FindProductQuery;
import com.microservice.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group")
@Slf4j
public class ProductEventHandler {
    private final ProductRepository productRepository;

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception re) throws Exception {
        // Log error Message
        throw re;
    }

    @ExceptionHandler(resultType = RuntimeException.class)
    public void handle(RuntimeException re)throws Exception{
        // Log error Message
        throw re;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) throws Exception {
        Product product = new Product();
        BeanUtils.copyProperties(productCreatedEvent, product);
        productRepository.saveAndFlush(product);
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) throws Exception {

        Product product = productRepository.findByProductId(productReservedEvent.getProductId());
        product.setQuantity(product.getQuantity() - productReservedEvent.getQuantity());
        productRepository.saveAndFlush(product);

        log.info("ProductReservedEvent handled for orderId {} and productId {}",
                productReservedEvent.getOrderId(), productReservedEvent.getProductId());
    }

    @EventHandler
    public void on(ProductReservationCancelledEvent productReservationCancelledEvent) throws Exception {

        Product product = productRepository.findByProductId(productReservationCancelledEvent.getProductId());
        product.setQuantity(product.getQuantity() + productReservationCancelledEvent.getQuantity());
        productRepository.saveAndFlush(product);

        log.info("ProductReservationCancelledEvent handled for orderId {} and productId {}",
                productReservationCancelledEvent.getOrderId(), productReservationCancelledEvent.getProductId());
    }
}
