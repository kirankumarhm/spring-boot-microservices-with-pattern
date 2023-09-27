package com.microservice.payment.example.model;

import com.microservice.corelibrary.model.PaymentDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    private String paymentId;
    @Column
    private String orderId;

//    @Column
//    private PaymentDetails paymentDetails;


}
