package com.microservice.product.service.controller;

import com.microservice.product.service.command.CreateProductCommand;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

@Component
@Slf4j
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {


    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {


        return (index, command) -> {
            log.info("Intercepted command : {}",command.getPayload());

            if(CreateProductCommand.class.equals(command.getPayload())) {
                CreateProductCommand productCommand = (CreateProductCommand) command.getPayload();
                if(productCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0){
                    throw new IllegalArgumentException("Price can not be less than or equal to zero.");
                }
                if(productCommand.getTitle() == null || productCommand.getTitle().isBlank()){
                    throw new IllegalArgumentException("Title can not be empty.");
                }
            }
            return command;
        };
    }
}
