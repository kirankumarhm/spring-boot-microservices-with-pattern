package com.microservice.product.service.repository;


import com.microservice.product.service.model.ProductLookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLookupRepository extends JpaRepository<ProductLookup, String> {
    ProductLookup findByProductId(String productId);
    ProductLookup findByProductIdOrTitle(String productId, String title);
}
