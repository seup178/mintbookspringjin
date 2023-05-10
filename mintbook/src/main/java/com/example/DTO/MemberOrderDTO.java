package com.example.DTO;

import java.util.List;

import com.example.domain.Member;
import com.example.domain.Order;

import lombok.Data;

@Data
public class MemberOrderDTO {

	private Member member;

	private List<Order> orders;
	
}
