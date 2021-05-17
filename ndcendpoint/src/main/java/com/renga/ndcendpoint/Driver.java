package com.renga.ndcendpoint;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.Consumer;
@EnableBinding({Driver.GatewayChannels.class})
@SpringBootApplication
public class Driver {

	 public static final UUID instanceUUID = UUID.randomUUID();

	    interface GatewayChannels {

	        

	        @Input("orderViewRS")
	        SubscribableChannel orderViewRS();

	        @Output("sanitizedOrderRQ")
	        MessageChannel sanitizedOrderRQ();
	    }

	    @MessagingGateway
	    public interface StreamGateway {
	        @Gateway(requestChannel = ENRICH, replyChannel = FILTER, replyTimeout = 1000L)
	        byte[] process(String payload);
	    }

	    private static final String ENRICH = "enrich";
	    private static final String FILTER = "filter";
 
	    
	public static void main(String[] args) {
		SpringApplication.run(Driver.class, args);
	}			
	

//	@Autowired
//	private StreamBridge streamBridge;

//	@Autowired
//	private OrderViewRSService service;
	
//	@Bean
//	public Function<Message<String>,String> consumeOrderViewRS(){
//		return (output) -> {
//			System.out.println("output received from OMS: "+"payload :"+output.getPayload() + "\n and header :"+output.getHeaders());
//			service.setOrderViewRS(output.getHeaders().get("orderIdentifier").toString(), output.getPayload());
//			return output.getPayload();
//			};
//	}

	
//	@Bean
//	public java.util.function.Consumer<String> orderViewRS() {
//	    return input -> {
//	        System.out.println("Received in ndcendpoint: " + input);
//	    };
//	}
//	@Bean
//	  public IntegrationFlow replyFiltererFlow() {
//	    return IntegrationFlows.from(Sink.INPUT).channel("orderViewRS")
//	        .get();
//	  }
//	
//	@Bean
//	  public IntegrationFlow headerEnricherFlow() {
//	    return IntegrationFlows.from("enrich")
//	    		.enrichHeaders(HeaderEnricherSpec::headerChannelsToString)
//	    		.channel("tokenized-orderRQ").get();
//	  }
	
	 @Bean
	    public IntegrationFlow headerEnricherFlow() {
	        return IntegrationFlows.from(ENRICH)
	                .enrichHeaders(HeaderEnricherSpec::headerChannelsToString)
	                .enrichHeaders(headerEnricherSpec -> headerEnricherSpec.header("instanceId",instanceUUID))
	                .channel("sanitizedOrderRQ").get();
	    }

	    @Bean
	    public IntegrationFlow replyFiltererFlow() {
	        return IntegrationFlows.from("orderViewRS")
	                .filter(Message.class, message -> instanceUUID.toString().equals(message.getHeaders().get("instanceId")))
	                .channel(FILTER)
	                .get();
	    }
	    
	    @RestController
	    public class TokenizerController {
	    	
	    	@Autowired
	    	TokenizerService tokenize;
	    	
	    	@Autowired
	    	private StreamBridge streamBridge;
	    	
	    	
	    	@Autowired
	    	OrderViewRSService orderViewRSService;
	    	
	    	@Autowired
	    	StreamGateway gateway;
	    	
	    	@Autowired
	    	SanitizerService sanitizeService;
	    	
//	    	@RequestMapping("/createOrder")
//	    	@ResponseBody
//	    	public String createOrder(@RequestBody String orderCreateRQ) {
//	    		 System.out.println("input received in ndcendpoint:" + orderCreateRQ);
//	    		 String tokenizedMsg = tokenize.tokenize(orderCreateRQ);
//	    		 MessageBuilder<String> outboundMessage = MessageBuilder.withPayload(tokenizedMsg).setHeader("orderIdentifier", tokenizedMsg.hashCode());
//	    		 System.out.println("order identifier before processing :"+tokenizedMsg.hashCode());
//	    		 streamBridge.send("tokenized-orderRQ",  outboundMessage.build());
//	    		 while(true) {
//	    			 String orderviewRS = service.getOrderViewRS(String.valueOf(tokenizedMsg.hashCode()));
//	    			 if(orderviewRS != null && !orderviewRS.isEmpty())
//	    				 return orderviewRS;
//	    		 }
//	    		// return "dummy order view RS";
//	    	}
	    	
	    	@RequestMapping("/createOrder")
	    	@ResponseBody
	    	public String createOrder(@RequestBody String orderCreateRQ) {
	    		 System.out.println("input received in ndcendpoint:" + orderCreateRQ);
	    		 orderCreateRQ = sanitizeService.sanitize(orderCreateRQ);
	    		 System.out.println(orderCreateRQ);
	    		 String tokenizedMsg = tokenize.tokenize(orderCreateRQ);
	    		 System.out.println(tokenizedMsg);
	    		// MessageBuilder<String> outboundMessage = MessageBuilder.withPayload(tokenizedMsg).setHeader("orderIdentifier", tokenizedMsg.hashCode());
	    		// System.out.println("order identifier before processing :"+tokenizedMsg.hashCode());
	    		 //streamBridge.send("tokenized-orderRQ",  outboundMessage.build());
	    		// return new String(gateway.process(tokenizedMsg));
	    		 new String(gateway.process(tokenizedMsg));
	    		 while(true) {
	    			 String orderviewRS = orderViewRSService.getOrderViewRS(String.valueOf(new String("").hashCode()));
	    			 if(orderviewRS != null && !orderviewRS.isEmpty())
	    				 return orderviewRS;
	    		 }
	    	}
	    }
}
