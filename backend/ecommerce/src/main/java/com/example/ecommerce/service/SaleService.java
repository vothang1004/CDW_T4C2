package com.example.ecommerce.service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.repository.OrderRepository;

public class SaleService {
	@Autowired
	private OrderRepository orderRepository;
//	public BigDecimal getSalesByCategoryAndMonth(Long categoryId, Month month) {
//	    List<Order> orders = orderRepository.findByCategoryIdAndMonth(categoryId, month);
//	    BigDecimal sales = BigDecimal.ZERO;
//	    for (Order order : orders) {
//	        if (!order.getPaymentMethod().equals("not payment yet")) {
//	            sales = sales.add(order.getTotalPrice());
//	        }
//	    }
//	    return sales;
//	}
    public BigDecimal getSalesByCategoryAndMonth(Long categoryId, Month month) {
        int monthValue = month.getValue();
        return orderRepository.findTotalAmountByCategoryIdAndMonthAndPayment(categoryId, monthValue);
    }
    
}
