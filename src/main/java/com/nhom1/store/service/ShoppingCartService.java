package com.nhom1.store.service;

import com.nhom1.store.domain.CartItem;
import com.nhom1.store.domain.User;
import com.nhom1.store.domain.Article;
import com.nhom1.store.domain.ShoppingCart;


public interface ShoppingCartService {

	ShoppingCart getShoppingCart(User user);
	
	int getItemsNumber(User user);
	
	CartItem findCartItemById(Long cartItemId);
	
	CartItem addArticleToShoppingCart(Article article, User user, int qty, String size);
		
	void clearShoppingCart(User user);
	
	void updateCartItem(CartItem cartItem, Integer qty);

	void removeCartItem(CartItem cartItem);
	
}
