package com.renga.ndctranslator;

import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
public class Driver {

	public static void main(String[] args) {
		SpringApplication.run(Driver.class, args);
	}			

//	@Autowired
//	private StreamBridge streamBridge;
	
	@Bean
	public Function<Message<String>,Message<String>> ndcTranslator(){
		return (input) -> {
			System.out.println("input received in ndc translator: "+input);
			//streamBridge.send("orderParamRQ", input+"converted into OrderParam");
			String replyMsg = input.getPayload()+": converted into OrderParam";
			return MessageBuilder.withPayload(replyMsg).copyHeaders(input.getHeaders()).build();
			//return input+": converted into OrderParam";
			};
	}
	
//	@Bean
//	public Function<String,String> createOrder(){
//		return (input) -> {
//			System.out.println("input received in OMS: "+input);
//			//streamBridge.send("orderParamRQ", input+"converted into OrderParam");
//			return input+"converted into orderviewRS";
//			};
//	}
	
	 
}
