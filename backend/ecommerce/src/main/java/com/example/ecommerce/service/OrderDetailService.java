package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.repository.OrderDetailRepository;

@Service
public class OrderDetailService {
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	public List<OrderDetail> getOrderDetails(Order order) {
		return orderDetailRepository.findByOrder(order);
	}
}
