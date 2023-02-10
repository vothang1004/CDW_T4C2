package com.example.ecommerce.serializer;

import java.io.IOException;

import com.example.ecommerce.model.Product;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ProductSerializer extends JsonSerializer<Product> {

	@Override
	public void serialize(Product value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// TODO Auto-generated method stub

	}

}
