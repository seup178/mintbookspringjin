package com.example.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.OrderDTO;
import com.example.domain.Member;
import com.example.domain.Order;
import com.example.security.SecurityUtil;
import com.example.service.MemberService;
import com.example.service.MypageService;

@RestController
public class MypageController {
	
	@Autowired
	private MypageService mypageService;
	
	@Autowired
	private MemberService memberService;
	
	
	
	//r
	@GetMapping("/api/mypage/read")
	public ResponseEntity getMypageAll() {
		
		String email = SecurityUtil.getCurrentEmail();
		Member findMember = new Member();
		findMember =memberService.findByEmail(email);
		
		List<Order> oders = mypageService.findByMember(findMember);
		
		return new ResponseEntity<>(oders, HttpStatus.OK);
	}
	
	//oder 상세
	@GetMapping("/api/mypage/read/detail")
	public ResponseEntity getMypageDetail(@RequestParam("no")int no,@RequestParam("id")int id, @RequestParam("num")String num){
		
		OrderDTO orderDto = new OrderDTO(); 
		
		Order order = mypageService.findById(no); 
		
		List<Order> orderss = mypageService.findByOrderNum(num);
	
		
//		orderMemberDto.setOrder(order);
//		orderMemberDto.setMember(member);
	
		
		// buyer; buyerAddress;   buyerEmail;   buyerPhone;   orderNum;  orderDate;  status;
		
		
		
		Member member = order.getMember();
		
		
		Order myorder = order;
		
		List<Order> orders = mypageService.findByMember(member);
		
	
		orderDto.setOrderss(orderss);
		
		orderDto.setOrders(orders);
		
		
		// orderDto.setMemberaddress(member.getAddress());
		
		orderDto.setBuyer(myorder.getBuyer());
		orderDto.setCartId(myorder.getCartId());
		orderDto.setBuyerAddress(myorder.getBuyerAdress());
		orderDto.setBuyerEmail(myorder.getBuyerEmail());
		//orderDto.setBuyer(myorder.buyer); 전화번호
		orderDto.setOrderNum(myorder.getOrderNum());
		orderDto.setOrderDate(myorder.getOrderDate());
		orderDto.setStatus(myorder.getStatus());
		orderDto.setPrice(myorder.getPrice());
		
		
		
	
		
		
		return new ResponseEntity<>(orderDto,HttpStatus.OK);
	}
	
}



























