package com.example.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.domain.Member;
import com.example.domain.Order;

import lombok.Data;

@Data
public class OrderDTO {

//	private Member member;
//	private Order order;
	
	
	// 주문자명 수령자명 배송지 전화번호 휴대번호 주문번호 배송방법 주문접수일 상태 수령예상일
	
	private String buyer;
	
	private String cartId;
	
	private String buyerAddress;
	
	private String buyerEmail;
	
	private String buyerPhone;
	
	private String orderNum;
	
	private LocalDate orderDate;
	
	private String status;
	
	private int price;
	
	private String payMethod;
	
	private int totalprice;
	
	private List<Order> orders;
	
	private List<Order> orderss;
	
	private List<BooksCountsDTO> booksWCount;
}
