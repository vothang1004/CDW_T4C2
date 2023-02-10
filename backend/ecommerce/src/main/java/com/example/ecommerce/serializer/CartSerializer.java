package com.example.ecommerce.serializer;

import java.io.IOException;
import java.util.Map;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CartSerializer extends JsonSerializer<Cart> {

	 @Override
	    public void serialize(Cart cart, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
	        jsonGenerator.writeStartObject();
	        jsonGenerator.writeNumberField("id", cart.getId());
	        jsonGenerator.writeNumberField("userId", cart.getUserId());
	        jsonGenerator.writeFieldName("productsWithAmount");
	        jsonGenerator.writeStartObject();
	        for (Map.Entry<Product, Integer> entry : cart.getProductsWithAmount().entrySet()) {
	            jsonGenerator.writeFieldName(entry.getKey().getId().toString());
	            jsonGenerator.writeStartObject();
	            jsonGenerator.writeNumberField("id", entry.getKey().getId());
	            jsonGenerator.writeStringField("name", entry.getKey().getName());
	            jsonGenerator.writeStringField("description", entry.getKey().getDescription());
	            jsonGenerator.writeNumberField("price", entry.getKey().getPrice());
	            jsonGenerator.writeNumberField("amount", entry.getValue());
	            jsonGenerator.writeEndObject();
	        }
	        jsonGenerator.writeEndObject();
	        jsonGenerator.writeEndObject();
	    }
}
