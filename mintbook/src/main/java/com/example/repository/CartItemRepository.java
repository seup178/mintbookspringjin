package com.example.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DTO.CartitemRequestDTO;
import com.example.domain.Book;
import com.example.domain.Cart;
import com.example.domain.CartItem;
import org.springframework.data.domain.Pageable;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	//카트아이템 저장
	void save(Cart newcart);

	//카트아이템에 데이터 조회
	CartItem findByCartIdAndBookId(Integer cartid, Integer bookid);
	
	//addItemToCart2
	
	CartItem findByCartidAndBookid(Cart cart, Book book);
	//장바구니 아이템 목록----------------------------------

	List<CartItem> findAllByCartid(Cart cart);

	List<CartItem> findByCartid(Cart cart);

	void deleteAllByIdIn(List<Integer> lists);

	//장바구니에 아이템 추가(여러개)
	List<CartItem> findAllByCartidAndBookidIn(Cart cart, List<Book> books);

	CartitemRequestDTO save(CartitemRequestDTO newDto);

	CartItem findByCartidAndBookid(Cart cart, int bookid);

	List<CartItem> findAllByIdIn(List<Integer> selectedids);

	Page<CartItem> findByCartid(Cart cart, Pageable pageable);
}
