package com.microservice.product.service;

import com.microservice.product.service.config.AxonConfig;
import com.microservice.product.service.controller.CreateProductCommandInterceptor;
import com.microservice.product.service.exception.ProductServiceEventErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
//import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import({ AxonConfig.class })
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Autowired
	public void registerInterceptor(ApplicationContext context, CommandBus bus){
		bus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}

	@Autowired
	public void configure(EventProcessingConfigurer configurer){
		configurer.registerListenerInvocationErrorHandler(
				"product-group",
				conf -> new ProductServiceEventErrorHandler()
		);

//		configurer.registerListenerInvocationErrorHandler(
//				"product-group",
//				conf -> PropagatingErrorHandler.instance()
//		);
	}
}
