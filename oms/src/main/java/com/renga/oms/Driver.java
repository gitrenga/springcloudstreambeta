package com.renga.oms;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.StreamListener;
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
	
//	@Bean
//	public Function<String,String> processNDC(){
//		return (input) -> {
//			System.out.println("input received in oms: "+input);
//			//streamBridge.send("orderParamRQ", input+"converted into OrderParam");
//			return input+"converted into orderViewRS";
//			};
//	}
																
	@Bean
	public Function<Message<String>,Message<String>> createOrder(){
		return (input) -> {
			System.out.println("input received in OMS: "+input);
			//streamBridge.send("orderParamRQ", input+"converted into OrderParam");
			//return MessageBuilder.withPayload(input+" :converted into orderviewRS").setHeader("key", Math)  ;
			String replyMsg = input+" :converted into orderviewRS";
			return MessageBuilder.withPayload(replyMsg).copyHeaders(input.getHeaders()).build();
			
			//return input+" :converted into orderviewRS";
			};
	}
	
	
	@Bean
	public Consumer<String> tokenResponse() {
		return System.out::println;
	}
	
	 
}
