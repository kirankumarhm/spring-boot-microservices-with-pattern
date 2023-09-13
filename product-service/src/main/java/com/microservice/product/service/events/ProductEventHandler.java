package com.microservice.product.service.events;

import com.microservice.product.service.dto.ProductRespose;
import com.microservice.product.service.events.ProductCreatedEvent;
import com.microservice.product.service.model.Product;
import com.microservice.product.service.query.FindProductQuery;
import com.microservice.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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


}
