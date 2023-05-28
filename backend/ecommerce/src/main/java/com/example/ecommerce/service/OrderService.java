package com.example.ecommerce.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
//import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.enumeration.PaymentState;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.OrderDetailRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private ProductRepository productRepository;
	@PersistenceContext
	private EntityManager entityManager;

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	/**
	 * remove 1. user --add 2. totalPrice 3. trackingNumber 4. orderStatus : update
	 * = "create order" remove orderdetails: remove price product 1.category 2.
	 * "name": "Smartphone", "description": "A smartphone", "price": 499.99,
	 * "productStock": 10, "linkImage": "https://example.com/image.jpg", "view":
	 * 100, "sale": 0.0, "createDate": "2022-02-21T10:00:00", "updateDate":
	 * "2022-02-21T10:00:00"
	 */
	/**
	 * order status will update by payment and other ship application to control
	 * default status : create order
	 * 
	 * @param order
	 * @param user
	 * @return
	 */
	@Transactional

	/*
	 * what the hell are those lines of code.???
	 * entityManager.getTransaction().begin(); entityManager.merge(order);
	 * entityManager.getTransaction().commit();
	 */
	public Order createOrder(Order order, User user) {
		// Set the order date to the current time
		order.setOrderDate(LocalDateTime.now());
		order.setUser(entityManager.merge(user));
		// entityManger.persist(user) is an example of Entitymanager
//        order.setUser(user);
		order.setOrderStatus("create order");
		order.setPaymentMethod(order.getPaymentMethod());
		order.setTrackingNumber(createTrackingNumber());
		// Save the order to the database
//        Order savedOrder = orderRepository.save(order);

		// Save the order details to the database
		for (OrderDetail detail : order.getOrderdetails()) {
			detail.setOrder(order);
			// get full product information.
			int l = detail.getProduct().getId();
			Product p = productRepository.findById(l).get();
			detail.setProduct(p);
			// calculate price
			BigDecimal quantity = new BigDecimal(detail.getQuantity());
			detail.setPrice(detail.getProduct().getPrice().multiply(quantity));

//            orderDetailRepository.save(detail);
		}

		// Calculate the total price of the order
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (OrderDetail detail : order.getOrderdetails()) {
			BigDecimal price = detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity()));
			totalPrice = totalPrice.add(price);
//			detail.setCreateDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
			detail.setCreateDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

			detail.setUpdateDate(detail.getCreateDate());
		}
		order.setTotalPrice(totalPrice);
//        System.out.println("test order data is correct ( manual check): "+order);

		Order savedOrder = orderRepository.save(order);
		return savedOrder;
	}

	public String createTrackingNumber() {
		int leftLimit = 48; // '0'
		int rightLimit = 122; // 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String trackingNumber = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return trackingNumber;
	}

	public Order getOrderById(long id) {
		Optional<Order> optionalOrder = orderRepository.findById(id);
		if (optionalOrder.isPresent()) {
			return optionalOrder.get();
		} else {
			throw new NotFoundException("Order not found");
//        	throw new RuntimeException("order not found");
		}
	}

	public void updateOrderStatus(int id, String status) {
		Order order = getOrderById(id);
		order.setOrderStatus(status);
		orderRepository.save(order);
	}

	public void updateOrderTrackingNumber(int id, String trackingNumber) {
		Order order = getOrderById(id);
		order.setTrackingNumber(trackingNumber);
		orderRepository.save(order);
	}

	public List<Order> getOrderByUser(User user) {
		// TODO Auto-generated method stub
//        return orderRepository.findByUserId(user.getId());
		return orderRepository.findByUser(user);

	}

	public BigDecimal calculateRevenue(LocalDateTime startDate, LocalDateTime endDate) {
		BigDecimal revenue = BigDecimal.ZERO;
//	    System.out.println("run he"+startDate);
		List<Order> orders = orderRepository.findByOrderDateBetween(startDate, endDate);
		for (Order order : orders) {
			revenue = revenue.add(order.getTotalPrice());
		}
		return revenue;
	}

	public Order getOrderByUser(User user, Long id) {
		// TODO Auto-generated method stub
		return orderRepository.findByUserAndId(user, id);
	}

	public Order updateState(Long id, PaymentState state) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(id).get();
		if (order == null)
			throw new NotFoundException("id order not found");
		order.setPaymentState(state);
		orderRepository.save(order);
		return order;
	}

}
