package com.microservice.product.service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table( name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(unique = true)
    private String productId;

    @Column(unique = true)
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
