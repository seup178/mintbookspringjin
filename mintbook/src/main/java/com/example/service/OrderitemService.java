package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Book;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.repository.OrderitemRepository;

@Service
public class OrderitemService {
	
	@Autowired
	OrderitemRepository orderitemRepository;
	
	public void save(OrderItem orderitem) {
		orderitemRepository.save(orderitem);
	}
	
	
	public List<OrderItem> findAllByOrderid(Order order) {
		return orderitemRepository.findAllByOrderid(order);
	}

	public List<Book> findBookidByIdIn(List<OrderItem> orderitemlist) {
		return orderitemRepository.findBookidByIdIn(orderitemlist);
	}

}
