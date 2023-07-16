package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DTO.BooklistResponseDTO;
import com.example.domain.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

	//도서명 검색
	Page<Book> findByBookNameContains(String content, Pageable pageable);

	//isbn 검색
	Page<Book> findByIsbnContains(String content, Pageable pageable);

	//저자명 검색
	Page<Book> findByAuthorContains(String content, Pageable pageable);

	//출판사 검색
	Page<Book> findByPublisherContains(String content, Pageable pageable);

	//베스트셀러 검색
	Page<Book> findAllByOrderByHitDesc(Pageable pageable);

	Book findById(Integer[] bookid);
	
	//검색결과페이지(혹시 오류나면 Bookname으로 고쳐보기)
	Page<Book> findByBookNameContainingOrAuthorContaining(String bookName, String author, Pageable pageable);
	
	List<Book> findAllByIdIn(List<Integer> lists);


}
