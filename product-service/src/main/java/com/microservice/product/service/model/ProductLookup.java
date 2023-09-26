package com.microservice.product.service.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table( name = "productlookup")
@AllArgsConstructor
@NoArgsConstructor
public class ProductLookup {

    @Id
    @Column(unique = true)
    private String productId;

    @Column(unique = true)
    private String title;
}
