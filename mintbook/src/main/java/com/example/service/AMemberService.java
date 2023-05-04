package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.domain.Member;
import com.example.repository.AmemberRepository;

@Service
public class AMemberService {

	@Autowired
	private AmemberRepository amemberRepository;

	public List<Member> findAll() {
		
		return amemberRepository.findAll();
	}

	public Page<Member> findAll(PageRequest pageRequest) {
		
		return amemberRepository.findAll(pageRequest);
	}

	public Member findById(int id) {
		
		return amemberRepository.findById(id).get();
	}

	public void delete(int id) {
		amemberRepository.deleteById(id);
	}
}
