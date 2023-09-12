package com.microservice.product.service.controller;

import com.microservice.product.service.command.CreateProductCommand;
import com.microservice.product.service.model.Product;
import com.microservice.product.service.model.ProductLookup;
import com.microservice.product.service.repository.ProductLookupRepository;
import com.microservice.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

//    private final ProductRepository productRepository;
    private final ProductLookupRepository productLookupRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {


        return (index, command) -> {
            log.info("Intercepted command : {}",command.getPayloadType());

            if(CreateProductCommand.class.equals(command.getPayloadType())) {

                CreateProductCommand productCommand = (CreateProductCommand) command.getPayload();

//                if(productCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0){
//                    throw new IllegalArgumentException("Price can not be less than or equal to zero.");
//                }
//                if(productCommand.getTitle() == null || productCommand.getTitle().isBlank()){
//                    throw new IllegalArgumentException("Title can not be empty.");
//                }

//                Product product = productRepository.findByProductIdOrTitle(productCommand.getProductId(), productCommand.getTitle());
                ProductLookup productLookup = productLookupRepository.findByProductIdOrTitle(productCommand.getProductId(), productCommand.getTitle());

                log.info("product : {}", productLookup);

                if(productLookup != null){
                    throw new IllegalStateException(String.format(
                            "Product with productId %s or title %s already exists"
                            ,productCommand.getProductId(), productCommand.getTitle()));
                }

            }
            return command;
        };
    }
}
