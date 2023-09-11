package com.microservice.product.service.controller;

import com.microservice.product.service.command.CreateProductCommand;
import com.microservice.product.service.dto.ProductRequest;
import com.microservice.product.service.dto.ProductRespose;
import com.microservice.product.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final Environment environment;
    private final CommandGateway commandGateway;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody ProductRequest productRequest){
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .title(productRequest.getTitle())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .productId(UUID.randomUUID().toString()).build();

        String returnValue;
        try {
            returnValue = commandGateway.sendAndWait(createProductCommand);
        } catch (Exception e) {
//            throw new RuntimeException(e);
            returnValue = e.getLocalizedMessage();
        }

        return returnValue;

//        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductRespose> getAllProducts(){
        return productService.getAllProducts();
    }

}
