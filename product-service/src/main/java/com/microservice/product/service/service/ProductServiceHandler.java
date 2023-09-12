package com.microservice.product.service.service;

import com.microservice.product.service.dto.ProductRespose;
import com.microservice.product.service.events.ProductCreatedEvent;
import com.microservice.product.service.model.Product;
import com.microservice.product.service.query.FindProductQuery;
import com.microservice.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ProductServiceHandler {
    private final ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        Product product = new Product();
        BeanUtils.copyProperties(productCreatedEvent, product);
        productRepository.saveAndFlush(product);
    }

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
