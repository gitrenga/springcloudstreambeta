package com.renga.ndcendpoint;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;
@Component
@MessagingGateway
public interface StreamGateway {
	@Gateway(requestChannel = "enrich", replyChannel = "orderViewRS")
	String process(String payload);

}
