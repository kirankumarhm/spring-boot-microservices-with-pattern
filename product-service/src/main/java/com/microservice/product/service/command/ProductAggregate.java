package com.microservice.product.service.command;

import com.microservice.corelibrary.command.ReserveProductCommand;
import com.microservice.corelibrary.events.ProductReservedEvent;
import com.microservice.product.service.events.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate(){

    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand)  {
        // Validate create product command

        if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price can not be less than or equal to zero.");
        }
        if(createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()){
            throw new IllegalArgumentException("Title can not be empty.");
        }

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);

    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand){
        if(quantity < reserveProductCommand.getQuantity()){
            throw new IllegalArgumentException("Insufficient number of items in stock");
        }

        ProductReservedEvent productReservedEvent = new ProductReservedEvent();
        BeanUtils.copyProperties(reserveProductCommand, productReservedEvent);
        AggregateLifecycle.apply(productReservedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        this.productId = productCreatedEvent.getProductId();
        this.title = productCreatedEvent.getTitle();
        this.price = productCreatedEvent.getPrice();
        this.quantity = productCreatedEvent.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent){
        this.quantity -= productReservedEvent.getQuantity();
    }
}
