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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.AMemberResponseDTO;
import com.example.DTO.MemberOrderDTO;
import com.example.DTO.QnaResponseDTO;
import com.example.domain.Member;
import com.example.domain.Order;
import com.example.domain.Qna;
import com.example.security.SecurityUtil;
import com.example.service.AMemberService;
import com.example.service.MemberService;
import com.example.service.MypageService;

import jakarta.transaction.Transactional;

@RestController
public class AMemberController {

	@Autowired
	private AMemberService aMemberService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MypageService mypageService;
	
	@GetMapping("/api/member/all")
	public ResponseEntity getAllMember(@RequestParam("page") int page) {
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
		
		Page<Member> memberpage;
		if(authok) {
			PageRequest pageRequest = PageRequest.of(page-1, 10);
			memberpage = aMemberService.findAll(pageRequest);
		}else {
			memberpage = null;
		}
		
		List<AMemberResponseDTO> memresp= new ArrayList<>();
		
		for(Member data:memberpage) {
			AMemberResponseDTO response = new AMemberResponseDTO();
			response.setId(data.getId());
			response.setEmail(data.getEmail());
			response.setName(data.getName());
			response.setBirth(data.getBirth());
			
						
			memresp.add(response);
			
		}
		return new ResponseEntity<>(memresp,HttpStatus.OK);

	}
	
	// 페이지네이션 
	@GetMapping("/api/member/allcnt")
	public ResponseEntity getMemberAllcnt() {
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
		
		List<Member> members;
		int count;
		if(authok) {
			members = aMemberService.findAll();
			count = members.size();
		}else {
			members = null;
			count = 0;
		}
		
		return new ResponseEntity<>(count, HttpStatus.OK);
		
		
		
	}
	
	
	//아이디 검색
	@GetMapping("/api/member/usersearch")
	public ResponseEntity qetMemberSearch(@RequestParam("search")String search) {
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
		
		List<Member> findmembers = memberService.findByEmailLike("%"+search+"%");

		List<AMemberResponseDTO> memresp= new ArrayList<>();
		
		for(Member data:findmembers) {
			AMemberResponseDTO response = new AMemberResponseDTO();
			response.setId(data.getId());
			response.setEmail(data.getEmail());
			response.setName(data.getName());
			response.setBirth(data.getBirth());
			
						
			memresp.add(response);
			
		}
		
		return new ResponseEntity<>(memresp, HttpStatus.OK);
		
	}
	
	

	//상세 페이지 
	@GetMapping("/api/member/detail")                                                    
	public ResponseEntity getMemberDetail(@RequestParam("no")int no,@RequestParam("email")String email) {
		
		MemberOrderDTO morderDTO = new MemberOrderDTO();
		
		Member member = aMemberService.findById(no);
		
		morderDTO.setMember(member);
		
		
		List<Order>  orders = mypageService.findByMember(member);

		morderDTO.setOrders(orders);
		
		//morderDTO.setOrders(members);
		
		return new ResponseEntity<>(morderDTO,HttpStatus.OK);
		
	}
	
	
	
	//회원삭제
	@Transactional
	@DeleteMapping("/api/member/delete/{id}")
	public ResponseEntity delteMember(@PathVariable("id")int id) {
		String email = SecurityUtil.getCurrentEmail();
		Member findWriter = new Member();
		findWriter =memberService.findByEmail(email); 
		
		aMemberService.delete(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
