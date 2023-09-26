package com.microservice.product.service.events;

import com.microservice.product.service.events.ProductCreatedEvent;
import com.microservice.product.service.model.Product;
import com.microservice.product.service.model.ProductLookup;
import com.microservice.product.service.repository.ProductLookupRepository;
import com.microservice.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group")
public class ProductLookupEventHandler {
    private final ProductLookupRepository  productLookupRepository;

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

        ProductLookup productLookup = new ProductLookup(productCreatedEvent.getProductId(), productCreatedEvent.getTitle());
        productLookupRepository.saveAndFlush(productLookup);
    }


}
