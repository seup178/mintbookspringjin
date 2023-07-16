package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.CartAddDto;
import com.example.DTO.CartitemRequestDTO;
import com.example.DTO.CartitemResponseDTO;
import com.example.domain.Book;
import com.example.domain.Cart;
import com.example.domain.CartItem;
import com.example.domain.Member;
import com.example.repository.BookRepository;
import com.example.repository.CartItemRepository;
import com.example.repository.CartRepository;
import com.example.repository.MemberRepository;
import com.example.security.SecurityUtil;
import com.example.service.BookService;
import com.example.service.CartItemService;
import com.example.service.CartService;
import com.example.service.MemberService;

import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired CartService cartService;
	
	@Autowired MemberService memberService;
	
	@Autowired CartItemService cartItemService;
	
	@Autowired BookService bookService;
	
	@Autowired
	private CartItemRepository cartitemRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	
	//카트 추가
	@PostMapping("/cart")
	public ResponseEntity addCart(@RequestBody List<Integer> bookids) {
		
		String email = SecurityUtil.getCurrentEmail();
		Member member = memberService.findByEmail(email);
		
		Cart cart = cartService.findByMemberId(member.getId());
		
		//카트 조회시 카트가 null이면 카트부터 저장해준다.
		if(cart == null) {
			Cart newcart = new Cart();
			newcart.setMember(member);
			
			cartService.save(newcart);
			cart = cartService.findByMemberId(member.getId());
		}
		
		//배열로 받아온 bookid값을 for문으로 꺼내서 저장한다.
	    for (int i = 0; i < bookids.size(); i++) {
	        Integer bookid = bookids.get(i);
	        CartItem cartitem = cartItemService.findByCartIdAndBookId(cart.getId(),bookid);
	        Book book = bookService.findById(bookid);
	        
	      //카트아이템이 없으면 새로 저장
			if(cartitem == null) {
				CartItem newitem = new CartItem();
				newitem.setBook(book);
				newitem.setCart(cart);
				newitem.setQuantity(1);
				cartItemService.save(newitem);
			} else {
				//기존등록정보가 있으면 기존 수량 +1
				cartitem.setQuantity(cartitem.getQuantity()+1);
				cartItemService.save(cartitem);
			}
	    }
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	
	//장바구니 담기(상세페이지에서)
		@PostMapping("/add/cartitem/{id}")
		public ResponseEntity addCartitem(@PathVariable("id") int id,@RequestBody CartitemRequestDTO dto) {
			
			Member member = memberService.findbyid(id);
			Cart cart = cartService.findByMemberid(member);
//			Cart cart = cartService.findById(Integer.parseInt(dto.getCartid()));
			Book book = bookService.findById(Integer.parseInt(dto.getBookid()));
			CartItem cartitem = cartItemService.findByCartidAndBookid(cart, book);

			//장바구니가 존재하지 않는 경우
			if(cart == null) {
				cart = Cart.createCart(member);
				cartRepository.save(cart);
			}
			
			//처음 등록되는 아이템일 경우
			if(cartitem == null) {
			
				CartItem addCartitem = new CartItem();
				
				addCartitem.setCart(cart);
				addCartitem.setBook(book);
				addCartitem.setCount(dto.getCount());
				
				CartItem result = cartItemService.save1(addCartitem);
			}
			
			//이미 등록된 아이템일 경우 수량만 증가
			else {
				CartItem update2 = cartitem;
//				update2.setCartid(cartitem.getCartid());
				update2.setBook(cartitem.getBook());
				update2.addCount(dto.getCount());
				cartitemRepository.save(update2);
			}
			
			return new ResponseEntity<>(HttpStatus.OK);
		}
	
		//장바구니 담기(목록에서)
		@PostMapping("/cartitem2/{id}/{ids}")//memberid, bookids
		public ResponseEntity addCartitem2(@PathVariable("id") int id, @PathVariable List<Integer> ids) {
			
			Member member = memberService.findbyid(id);//member
			Cart cart = cartService.findByMemberid(member);//cart
			
			//[1,2,3]
			List<Integer> bookids = ids.stream().filter(Objects::nonNull).collect(Collectors.toList());
			
			//장바구니가 존재하지 않는 경우
			if(cart == null) {
				cart = Cart.createCart(member);
				cartRepository.save(cart);
			}

			//배열로 받아온 bookid값을 for문으로 꺼내서 저장
			for(int i=0 ; i<bookids.size() ; i++) {
				int bookid = bookids.get(i);
				Book book = bookService.findById(bookid);
				CartItem cartitem = cartItemService.findByCartidAndBookid(cart, book);
				
				if(cartitem == null) {
					CartItem newitem = new CartItem();
					newitem.setBook(book);
					newitem.setCart(cart);
					newitem.setCount(1);
					cartItemService.save(newitem);
				}else {
					cartitem.setCount(cartitem.getCount()+1);
					cartItemService.save(cartitem);
				}
				
			}		
			
			return new ResponseEntity<>(HttpStatus.OK);
		 
		}
		
		//장바구니 추가(목록의 각 행에 있는 버튼에서)
		@PostMapping("/add/cartitem3/{id}/{bookid}")//memberid, bookids
		public ResponseEntity addCartitem3(@PathVariable("id") int id, @PathVariable("bookid") int bookid) {
			
			Member member = memberService.findbyid(id);
			Cart cart = cartService.findByMemberid(member);
			Book book = bookService.findById(bookid);
			CartItem cartitem = cartItemService.findByCartidAndBookid(cart, book);
			
			//장바구니가 존재하지 않는 경우
			if(cart == null) {
				cart = Cart.createCart(member);
				cartRepository.save(cart);
			}

			//처음 등록되는 상품이 경우
			if(cartitem == null) {
				CartItem newitem = new CartItem();
				newitem.setBook(book);
				newitem.setCart(cart);
				newitem.setCount(1);
				cartItemService.save(newitem);
			}else {
				cartitem.setCount(cartitem.getCount()+1);
				cartItemService.save(cartitem);
			}
					
			
			return new ResponseEntity<>(HttpStatus.OK);
		 
		}
		
		//장바구니 목록
		@GetMapping("/get/cartitem/{id}")
		public ResponseEntity CartitemList(@PathVariable("id") int id,
										   @PageableDefault(size=10, 
									       					sort = "id", 
							       							direction = Sort.Direction.DESC) 
															Pageable pageable){
			
			Member member = memberService.findbyid(id);//memberid
			Cart cart = cartRepository.findByMemberId(member);//cartid
			
			Page<CartItem> cartitems = cartitemRepository.findByCartid(cart, pageable);

			Page<CartitemResponseDTO> dto = new CartitemResponseDTO().toDtoList(cartitems);
//			List<CartitemResponseDTO> dtos = cartitems.stream().map(CartitemResponseDTO::new)
//															   .collect(Collectors.toList());
			
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}
		
		@Transactional
		@DeleteMapping("/delete/cartitem/{ids}")
		List<Integer> deleteCartitems(@PathVariable List<Integer> ids){
			// ids입력 > int형 리스트로 전환 > 리스트에 해당하는 cartitem 호출
			// dto로 ids 입력 > int형
			List<Integer> lists = ids.stream().filter(Objects::nonNull).collect(Collectors.toList());
			
			cartitemRepository.deleteAllByIdIn(lists);
			
			return lists;
		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
