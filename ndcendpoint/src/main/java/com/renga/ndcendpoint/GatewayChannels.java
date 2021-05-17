package com.renga.ndcendpoint;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
interface GatewayChannels {

    //String TO_UPPERCASE_REPLY = "to-uppercase-reply";
    //String TO_UPPERCASE_REQUEST = "to-uppercase-request";

	@Output("orderViewRS")
	MessageChannel  orderViewRS();

    @Input("tokenizedOrderRQ")
    SubscribableChannel tokenizedOrderRQ();
  }