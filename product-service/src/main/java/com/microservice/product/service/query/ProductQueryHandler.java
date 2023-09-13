package com.microservice.product.service.query;

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
public class ProductQueryHandler {
    private final ProductRepository productRepository;


    @QueryHandler
    public List<ProductRespose> findProducts(FindProductQuery productQuery){
        List<Product> productList = productRepository.findAll();

        List<ProductRespose> productResposeList = productList.stream().map(product ->
                ProductRespose.builder()
                        .price(product.getPrice())
                        .title(product.getTitle())
                        .quantity(product.getQuantity())
                        .productId(product.getProductId())
                        .build()
        ).collect(Collectors.toList());

        return productResposeList;
    }
}
