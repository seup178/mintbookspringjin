package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Member;
import com.example.domain.Order;
import com.example.repository.MemberRepository;
import com.example.repository.OrderRepository;

@Service
public class MypageService {
	
	@Autowired OrderRepository orderRepository;
	@Autowired MemberRepository memberRepository;

	public List<Order> findByMember(Member findMember) {
		
		return orderRepository.findByMember(findMember);
	}

	public Order findById(int id) {
		
		return orderRepository.findById(id).get();
	}

	public Member findByMemberId(int id) {
		
		return memberRepository.findById(id).get();
	}

	public List<Order> findByOrderNum(String num) {
		
		return orderRepository.findByOrderNum(num);
	}

	public List<Order> findByMemberId(String num) {
		
		return orderRepository.findByMemberId(num);
	}

	



}
