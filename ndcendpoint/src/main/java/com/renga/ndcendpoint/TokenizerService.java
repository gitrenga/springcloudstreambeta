package com.renga.ndcendpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class TokenizerService {
	
	@Autowired
	private StreamBridge streamBridge;
	
	public String tokenize(String OrderCreateRQ) {
		//System.out.println("tokenize called");
		streamBridge.send("tokenRequest", OrderCreateRQ);
		return OrderCreateRQ+":token request sent";
	}

}
