package com.renga.ndcendpoint;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class OrderViewRSService {

	Map<String,String> orderViewRSMap = new HashMap<String,String>();
	
	public String getOrderViewRS(String orderIdentifier) {
		String orderViewRS = orderViewRSMap.get(orderIdentifier);
		orderViewRSMap.remove(orderIdentifier);
		return orderViewRS;
	}
	
	public void setOrderViewRS(String orderIdentifier,String orderViewRS) {
		orderViewRSMap.put(orderIdentifier,orderViewRS);
	}
}
