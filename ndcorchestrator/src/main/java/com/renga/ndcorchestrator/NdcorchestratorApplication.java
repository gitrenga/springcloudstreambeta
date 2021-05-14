package com.renga.ndcorchestrator;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NdcorchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NdcorchestratorApplication.class, args);
	}

	@Bean
	public Function<OrderRQ,OrderRS> createOrder(){
		return (input) -> {System.out.println("input received: "+input.getInput());return new OrderRS();};
	}
}
