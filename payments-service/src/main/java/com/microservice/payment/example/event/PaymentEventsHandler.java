package com.microservice.payment.example.event;

import com.microservice.corelibrary.events.PaymentProcessedEvent;
import com.microservice.payment.example.model.Payment;
import com.microservice.payment.example.repository.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventsHandler {
    private final PaymentsRepository paymentRepository;

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        log.info("PaymentProcessedEvent is called for orderId: " + event.getOrderId());

        Payment paymentEntity = new Payment();
        BeanUtils.copyProperties(event, paymentEntity);

        paymentRepository.saveAndFlush(paymentEntity);

    }
}
