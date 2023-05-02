package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.DTO.QnaDTO;
import com.example.domain.Member;
import com.example.domain.Qna;
import com.example.security.SecurityUtil;
import com.example.service.MemberService;
import com.example.service.QnaService;

import jakarta.transaction.Transactional;

@RestController
public class QnaController {

	@Autowired
	private QnaService qnaService;
	@Autowired
	private MemberService memberService;
	
	//c
	@PostMapping("/api/qna/add")
	public ResponseEntity addQna(@RequestBody QnaDTO dto) {
		Qna newQna = new Qna();
		newQna.setQnaTitle(dto.getQnaTitle());
		newQna.setContent(dto.getContent());
		
		String email = SecurityUtil.getCurrentEmail();
		newQna.setWriter(memberService.findByEmail(email));
		
		Qna result =  qnaService.save(newQna);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//c admin
	@Transactional
	@PostMapping("/api/qna/reply/{id}")
	public ResponseEntity addReply(@PathVariable("id") int id, @RequestBody QnaDTO dto) {
		String email = SecurityUtil.getCurrentEmail();
		Member findWriter = new Member();
		findWriter =memberService.findByEmail(email);
		
		List<String> auth = findWriter.getRoles();
		
		boolean authok=false;
		for(String rdata :auth) {
			if(rdata.equals("ADMIN")) {
				authok = true;
			}
		}
			
		List<Qna> qnas;
		if(authok) {
			Qna newQna = qnaService.findById(id);
			newQna.update(dto.getReply());
		}else {
			qnas = null;
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	//r
	@GetMapping("/api/qna/read")
	public ResponseEntity getQnaAll() {
		
		String email = SecurityUtil.getCurrentEmail();
		Member findWriter = new Member();
		findWriter =memberService.findByEmail(email);
		
		List<Qna> qnas = qnaService.findByWriter(findWriter);
		
		return new ResponseEntity<>(qnas, HttpStatus.OK);
	}
	
	
	@GetMapping("/api/mypage/inquire/detail")
	public ResponseEntity getInquireDetail(@RequestParam("no") int no) {
		
		Qna qna = qnaService.findById(no);
		
		return new ResponseEntity<>(qna,HttpStatus.OK);
	}
	
	
	//r admin
	@GetMapping("/api/qna/all")
	public ResponseEntity getQnaAllAdmin() {
		
		String email = SecurityUtil.getCurrentEmail();
		Member findWriter = new Member();
		findWriter =memberService.findByEmail(email);
			
		List<String> auth = findWriter.getRoles();
		
		boolean authok=false;
		for(String rdata :auth) {
			if(rdata.equals("ADMIN")) {
				authok = true;
			}
		}
			
		List<Qna> qnas;
		if(authok) {
			qnas = qnaService.findAll();
		}else {
			qnas = null;
		}
		return new ResponseEntity<>(qnas, HttpStatus.OK);
	}
	
	
	//u
	@PutMapping("/api/qna/edit/{id}")
	public ResponseEntity editQna(@PathVariable("id") int id, @RequestBody QnaDTO dto) {
		String email = SecurityUtil.getCurrentEmail();
		Member findWriter = new Member();
		findWriter =memberService.findByEmail(email);
		
		Qna newQna = qnaService.findById(id);
		newQna.update(dto.getContent());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}
