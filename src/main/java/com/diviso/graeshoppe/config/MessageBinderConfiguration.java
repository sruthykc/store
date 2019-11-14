package com.diviso.graeshoppe.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageBinderConfiguration {

	String STORE="store";
	
	@Output(STORE)
	MessageChannel storeOut();
	
}
