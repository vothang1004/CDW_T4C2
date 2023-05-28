package com.example.ecommerce.service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.enumeration.PaymentMethod;
import com.example.ecommerce.enumeration.PaymentState;
import com.example.ecommerce.repository.OrderRepository;

@Service
public class SaleService {
	@Autowired
	private OrderRepository orderRepository;
	@PersistenceContext
	private EntityManager entityManager;

	public List<Order> getOrdersByCategoryAndMonth(Long categoryId, int year, Month month) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Order> orderRoot = cq.from(Order.class);
		Join<Order, OrderDetail> orderDetailJoin = orderRoot.join("orderdetails");
		Join<OrderDetail, Product> productJoin = orderDetailJoin.join("product");
		Join<Product, Category> categoryJoin = productJoin.join("category");

		cq.select(orderRoot).distinct(true).where(cb.equal(categoryJoin.get("id"), categoryId),
				cb.equal(cb.function("year", Integer.class, orderRoot.get("orderDate")), year),
				cb.equal(cb.function("month", Integer.class, orderRoot.get("orderDate")), month.getValue()));

		List<Order> orders = entityManager.createQuery(cq).getResultList();
		return orders;
	}

	public BigDecimal getSalesByCategoryAndMonth(Long categoryId, Month month) {
//	    List<Order> orders = orderRepository.findByCategoryIdAndMonth(categoryId, month);
		List<Order> orders = getOrdersByCategoryAndMonth(categoryId, 2023, month);

		BigDecimal sales = BigDecimal.ZERO;
		for (Order order : orders) {
			if (order.getPaymentState().equals(PaymentState.paid)) {
				sales = sales.add(order.getTotalPrice());
			}
		}
		return sales;
//		return null;
	}
//    public BigDecimal getSalesByCategoryAndMonth(Long categoryId, Month month) {
//        int monthValue = month.getValue();
//        return orderRepository.findTotalAmountByCategoryIdAndMonthAndPayment(categoryId, monthValue);
//    }

}
