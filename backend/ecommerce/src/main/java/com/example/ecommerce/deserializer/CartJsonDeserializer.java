package com.example.ecommerce.deserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.model.Cart2;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CartJsonDeserializer extends JsonDeserializer<Cart2> {
	@Override
	public Cart2 deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		JsonNode node = jsonParser.getCodec().readTree(jsonParser);
//		Long id = node.get("id").asLong();
		Long userId = node.get("userId").asLong();
		Map<Product, Integer> productsWithAmount = new HashMap<>();
		JsonNode productsNode = node.get("productsWithAmount");
		for (Iterator<Map.Entry<String, JsonNode>> it = productsNode.fields(); it.hasNext();) {
			Map.Entry<String, JsonNode> entry = it.next();
			JsonNode productNode = entry.getValue();
			Long productId = productNode.get("id").asLong();
			String name = productNode.get("name").asText();
			String description = productNode.get("description").asText();
			Double price = productNode.get("price").asDouble();
			Integer amount = productNode.get("amount").asInt();
//			Product product = new Product(productId, name, description, price);
			Product product = new Product();

			productsWithAmount.put(product, amount);
		}
		return new Cart2(userId, productsWithAmount);
	}
}