package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Member;
import com.example.domain.Order;
import com.example.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	public void save(Order order) {
		orderRepository.save(order);
	}
	
	
	public Order findByMemberid(Member member) {
		return orderRepository.findByMemberid(member);
	}

	public Order findById(int orderid) {
		return orderRepository.findById(orderid).get();
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	
	
	

}
