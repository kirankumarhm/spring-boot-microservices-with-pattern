package com.microservice.example.query;

import com.microservice.corelibrary.model.PaymentDetails;
import com.microservice.corelibrary.model.User;
import com.microservice.corelibrary.query.FetchUserPaymentDetailsQuery;

import lombok.extern.slf4j.Slf4j;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEventsHandler {

    @QueryHandler
    public User findUserPaymentDetails(FetchUserPaymentDetailsQuery query){
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("SERGEY KARGOPOLOV")
                .validUntilMonth(12)
                .validUntilYear(2030)
                .build();

        log.debug("paymentDetails : {} ",paymentDetails.toString() );

        return User.builder()
                .firstName("Sergey")
                .lastName("Kargopolov")
                .userId(query.getUserId())
                .paymentDetails(paymentDetails)
                .build();
    }
}
