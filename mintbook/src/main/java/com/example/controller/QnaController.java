package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.DTO.QnaDTO;
import com.example.DTO.QnaResponseDTO;
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
		
		String email = SecurityUtil.getCurrentEmail();
		Member findWriter = new Member();
		findWriter =memberService.findByEmail(email);
		
		Qna newQna = new Qna();
		newQna.setQnaTitle(dto.getQnaTitle());
		newQna.setContent(dto.getContent());
		
		// String email = SecurityUtil.getCurrentEmail();
		newQna.setWriter(memberService.findByEmail(email));
		
		Qna result =  qnaService.save(newQna);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//c,u admin
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
	@Transactional
	@GetMapping("/api/qna/all")
	public ResponseEntity getQnaAllAdmin(@RequestParam("page") int page) {
		
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
			
		Page<Qna> qnas;
		if(authok) {
			PageRequest pageRequest = PageRequest.of(page-1, 10);
			//qnas = qnaService.findAll();
			qnas = qnaService.findAll(pageRequest);
			
		}else {
			qnas = null;
		}
		
		List<QnaResponseDTO> qnaresp= new ArrayList<>();
		
		for(Qna data:qnas) {
			QnaResponseDTO response = new QnaResponseDTO();
			response.setId(data.getId());
			response.setQnaTitle(data.getQnaTitle());
			response.setContent(data.getContent());
			response.setReg_date(data.getReg_date());
			response.setReply(data.getReply());
			
			if(data.getWriter() != null) {
				response.setWriter(data.getWriter().getEmail());	
			}else {
				response.setWriter(null);
			}
			
			qnaresp.add(response);
			
		}
		
		return new ResponseEntity<>(qnaresp, HttpStatus.OK);
	}
	// Pagination
	@GetMapping("/api/qna/allcnt")
	public ResponseEntity getQnaAllAdmincnt() {
		
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
		int count;
		if(authok) {
			qnas = qnaService.findAll();
			count = qnas.size();
		}else {
			qnas = null;
			count = 0;
		}
		
		
		
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
	
	//유저 검색
	@GetMapping("/api/qna/usersearch")
	public ResponseEntity getQnaSearch(@RequestParam("search")String search) {
		
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
		
		//검색
		
		List<Qna> searchList = new ArrayList<>();
		//Member findmember = memberService.findByEmail(search);
		List<Member> findmembers = memberService.findByEmailLike("%"+search+"%");
		
		List<Member> searchEmail =new ArrayList<>();
		for(Member m:findmembers) {
			
			searchEmail.add(m);
		}
		searchList = qnaService.findByWriterIn(searchEmail);
				
	
		
		List<Qna> qnas=searchList;
		List<QnaResponseDTO> qnaresp= new ArrayList<>();
		
		for(Qna data:qnas) {
			QnaResponseDTO response = new QnaResponseDTO();
			response.setId(data.getId());
			response.setQnaTitle(data.getQnaTitle());
			response.setContent(data.getContent());
			response.setReg_date(data.getReg_date());
			response.setReply(data.getReply());
	
			if(data.getWriter() != null) {
				response.setWriter(data.getWriter().getEmail());	
			}else {
				response.setWriter(null);
			}
			
			qnaresp.add(response);
			
		}
		
		return new ResponseEntity<>(qnaresp, HttpStatus.OK);
		
		
	}
		
	//admin 상세
	@GetMapping("/api/mypage/inquire/detailadmin")
	public ResponseEntity getInquireDetailadmin(@RequestParam("no") int no) {
		
		Qna qna = qnaService.findById(no);
		QnaResponseDTO dto = new QnaResponseDTO();
		dto.setId(qna.getId());
		dto.setContent(qna.getContent());
		dto.setQnaTitle(qna.getQnaTitle());
		dto.setReg_date(qna.getReg_date());
		dto.setReply(qna.getReply());
		
		if(qna.getWriter().getEmail() != null) {
			dto.setWriter(qna.getWriter().getEmail());
		}else {
			dto.setWriter(null);
		}
		
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	
	
	//u
	@Transactional
	@PutMapping("/api/qna/edit/{id}")
	public ResponseEntity editQna(@PathVariable("id") int id, @RequestBody QnaDTO dto) {
		String email = SecurityUtil.getCurrentEmail();
		Member findWriter = new Member();
		findWriter =memberService.findByEmail(email);
		
		Qna newQna = qnaService.findById(id);
		newQna.updateTitleContent(dto.getQnaTitle() ,dto.getContent());
		return new ResponseEntity<>(newQna, HttpStatus.OK);
	}
	
	//d
	@Transactional
	@DeleteMapping("/api/qna/delete/{id}")
	public ResponseEntity deleteQna(@PathVariable("id")int id) {
		String email = SecurityUtil.getCurrentEmail();
		Member findWriter = new Member();
		findWriter =memberService.findByEmail(email);
		
		qnaService.delete(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}
