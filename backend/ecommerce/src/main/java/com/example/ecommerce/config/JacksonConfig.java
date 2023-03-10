package com.example.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ecommerce.deserializer.CartJsonDeserializer;
import com.example.ecommerce.model.Cart2;
import com.example.ecommerce.serializer.CartSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(Cart2.class, new CartSerializer());
		module.addDeserializer(Cart2.class, new CartJsonDeserializer());
		objectMapper.registerModule(module);
		objectMapper.registerModule(javaTimeModule());
		return objectMapper;
	}

	@Bean
	public JavaTimeModule javaTimeModule() {
		return new JavaTimeModule();
	}

}
