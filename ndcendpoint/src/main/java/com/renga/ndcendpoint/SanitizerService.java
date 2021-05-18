package com.renga.ndcendpoint;

import org.springframework.stereotype.Component;

@Component
public class SanitizerService {
	
	public String sanitize(String OrderRQ) {
		return OrderRQ + ": sanitized in ndcendpoint";
	}

}
