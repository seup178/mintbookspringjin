package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Book;
import com.example.domain.Order;
import com.example.domain.OrderItem;

public interface OrderitemRepository extends JpaRepository<OrderItem, Integer>{
	
	List<OrderItem> findAllByOrderid(Order order);

	List<Book> findBookidByIdIn(List<OrderItem> orderitemlist);

}
