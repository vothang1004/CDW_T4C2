package com.example.ecommerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.OrderDetailDto;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.enumeration.PaymentMethod;
import com.example.ecommerce.enumeration.PaymentState;
import com.example.ecommerce.service.JwtTokenUtil;
import com.example.ecommerce.service.OrderDetailService;
import com.example.ecommerce.service.OrderService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private JwtTokenUtil tokenService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService orderDetailService;

	@GetMapping
	public List<OrderDto> getOrderByToken(HttpServletRequest request) {
		User user = tokenService.getUserByRequest(request);

		List<Order> orders = orderService.getOrderByUser(user);
		return orders.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	@GetMapping("/pay-method")
	public PaymentMethod[] paymentMethod(){
		return PaymentMethod.values();
	}
	@GetMapping("/pay-state")
	public PaymentState[] paymentState(){
		return PaymentState.values();
	}
	@PutMapping("/pay-state/{id}/{state}")
	public ResponseEntity<OrderDto> paymentState(@PathVariable Long id, @PathVariable PaymentState state){
		Order orders = orderService.updateState(id,state);
		return ResponseEntity.ok(convertToDto(orders));
	}
	@GetMapping("/{id}")
	public OrderDto getOrderByToken(HttpServletRequest request,@PathVariable Long id) {
		User user = tokenService.getUserByRequest(request);
		Order orders = orderService.getOrderByUser(user,id);
		return convertToDto(orders);
	}
	/*
	 * standard api for order controller create { "shippingAddress": "123 Main St",
	 * "billingAddress": "456 Elm St", "paymentMethod": "Credit Card", "notes":
	 * "Please handle with care", "orderDetailDtos": [ { "product": { "id": 1 },
	 * "quantity": 1, "discount": 0.1, "tax": 0.05 }, { "product": { "id": 2 },
	 * "quantity": 2, "discount": 0.1, "tax": 0.1 } ] }
	 */
	@PostMapping
	public OrderDto createOrder(HttpServletRequest request, @RequestBody OrderDto orderDto) {
		/**
		 * remove 1. user 2. totalPrice 3. trackingNumber 4. orderStatus remove
		 * orderdetails: remove price product 1.category 2. "name": "Smartphone",
		 * "description": "A smartphone", "price": 499.99, "productStock": 10,
		 * "linkImage": "https://example.com/image.jpg", "view": 100, "sale": 0.0,
		 * "createDate": "2022-02-21T10:00:00", "updateDate": "2022-02-21T10:00:00"
		 */
		User user = tokenService.getUserByRequest(request);
		Order order = convertToEntity(orderDto);
		Order savedOrder = orderService.createOrder(order, user);
		return convertToDto(savedOrder);
	}

//    @PostMapping("/{orderId}/pay")
//    public void payOrder(@PathVariable("orderId") Long orderId, @RequestBody PaymentDto paymentDto) {
//        orderService.payOrder(orderId, paymentDto);
//    }

	private OrderDto convertToDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setUser(order.getUser());
		orderDto.setOrderDate(order.getOrderDate());
		orderDto.setShippingAddress(order.getShippingAddress());
		orderDto.setBillingAddress(order.getBillingAddress());
		orderDto.setTotalPrice(order.getTotalPrice());
		orderDto.setTrackingNumber(order.getTrackingNumber());
		orderDto.setPaymentMethod(order.getPaymentMethod());
		orderDto.setPaymentState(order.getPaymentState());
		orderDto.setOrderStatus(order.getOrderStatus());
		orderDto.setNotes(order.getNotes());
		orderDto.setOrderDetailDtos(orderDetailService.getOrderDetails(order).stream().map(this::convertToDto)
				.collect(Collectors.toList()));
		return orderDto;
	}

	private OrderDetailDto convertToDto(OrderDetail orderDetail) {
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		orderDetailDto.setId(orderDetail.getId());
		orderDetailDto.setProduct(orderDetail.getProduct());
		orderDetailDto.setQuantity(orderDetail.getQuantity());
		orderDetailDto.setPrice(orderDetail.getPrice());
		orderDetailDto.setDiscount(orderDetail.getDiscount());
		orderDetailDto.setTax(orderDetail.getTax());
		return orderDetailDto;
	}

	private Order convertToEntity(OrderDto orderDto) {
		Order order = new Order();
		if (orderDto.getUser() != null) {
			order.setUser(new User(orderDto.getUser()));

		}
		order.setOrderDate(orderDto.getOrderDate());
		order.setShippingAddress(orderDto.getShippingAddress());
		order.setBillingAddress(orderDto.getBillingAddress());
		order.setTotalPrice(orderDto.getTotalPrice());
		order.setTrackingNumber(orderDto.getTrackingNumber());
		order.setPaymentMethod(orderDto.getPaymentMethod());
		order.setPaymentState(orderDto.getPaymentState());
		order.setOrderStatus(orderDto.getOrderStatus());
		order.setNotes(orderDto.getNotes());
		List<OrderDetail> orderDetails = orderDto.getOrderDetailDtos().stream().map(this::convertToEntity)
				.collect(Collectors.toList());
		order.setOrderdetails(orderDetails);
//              setOrderdetails
		return order;
	}

	private OrderDetail convertToEntity(OrderDetailDto orderDetailDto) {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setProduct(new Product(orderDetailDto.getProduct()));
		orderDetail.setQuantity(orderDetailDto.getQuantity());
		orderDetail.setPrice(orderDetailDto.getPrice());
		orderDetail.setDiscount(orderDetailDto.getDiscount());
		orderDetail.setTax(orderDetailDto.getTax());
		return orderDetail;
	}
//	@GetMapping
//	public List<OrderDto> getOrderByToken(HttpServletRequest request) {
//		User user = tokenService.getUserByRequest(request);
//
//		List<Order> orders = orderService.getAllOrders();
//		for (Order order : orders) {
//			for (OrderDetail order2 : order.getOrderdetails()) {
//				System.out.println(order2.getProduct());
//			}
//		}
//		return orders.stream().map(this::convertToDto).collect(Collectors.toList());
//	}
}
