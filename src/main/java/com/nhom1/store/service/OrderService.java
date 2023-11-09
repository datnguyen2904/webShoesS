package com.nhom1.store.service;

import java.util.List;

import com.nhom1.store.domain.Shipping;
import com.nhom1.store.domain.User;
import com.nhom1.store.domain.Order;
import com.nhom1.store.domain.ShoppingCart;

public interface OrderService {
	List<Order> findAllOrders();

	Order getById(Long id);

	void updateOrder(Order order);

	Order createOrder(ShoppingCart shoppingCart, Shipping shippingAddress, User user);
	
	List<Order> findByUser(User user);
	
	Order findOrderWithDetails(Long id);

}
