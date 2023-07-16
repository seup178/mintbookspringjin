package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Book;
import com.example.domain.Cart;
import com.example.domain.CartItem;
import com.example.repository.BookRepository;
import com.example.repository.CartItemRepository;
import com.example.repository.CartRepository;

@Service
public class CartItemService {
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	BookRepository bookRepository;

	public CartItem findByCartIdAndBookId(Integer cartid, Integer  bookid) {
		return cartItemRepository.findByCartIdAndBookId(cartid, bookid);
	}

	public void save(CartItem newitem) {
		cartItemRepository.save(newitem);
	}
	
	public Cart findByMemberid(int id) {
		return cartRepository.findByMemberId(id);
	}
	
	public CartItem findByCartidAndBookid(Cart cart, Book book) {
		return cartItemRepository.findByCartidAndBookid(cart, book);
	}
	
	public CartItem save1(CartItem cartitem) {
		return cartItemRepository.save(cartitem);
	}
	
	public void deleteById(CartItem cartitem) {
		cartItemRepository.delete(cartitem);
	}
	
	public List<CartItem> findAllByIdIn(List<Integer> selectedids) {
		return cartItemRepository.findAllByIdIn(selectedids);
	}
	
	public CartItem findById(int cartitemid) {
		return cartItemRepository.findById(cartitemid).get();
	}
	
	public void deleteAllByIdIn(List<Integer> selectedids) {
		cartItemRepository.deleteAllByIdIn(selectedids);
	}
	
	
}
