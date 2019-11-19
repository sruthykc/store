package com.diviso.graeshoppe.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageBinderConfiguration {

	String STORE="store";
	String STORE_ADDRESS="storeAddress";
	@Output(STORE)
	MessageChannel storeOut();
	@Output(STORE_ADDRESS)
	MessageChannel storeAddressOut();
}
