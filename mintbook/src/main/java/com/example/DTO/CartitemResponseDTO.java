package com.example.DTO;

import com.example.domain.Book;
import com.example.domain.Cart;
import com.example.domain.CartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.domain.Page;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartitemResponseDTO {
	
	private Book bookid;
	private int id;
	private Cart cartid;
	
	private int count;
	
	public CartitemResponseDTO(CartItem entity) {
		this.id = entity.getId();
		this.cartid = entity.getCart();
		this.bookid = entity.getBook();
		this.count = entity.getCount();
	}
	
	public Page<CartitemResponseDTO> toDtoList(Page<CartItem> cartitems) {
		Page<CartitemResponseDTO> cartitemDtoList = cartitems.map(m-> 
		CartitemResponseDTO.builder()
						.id(m.getId())
						.cartid(m.getCart())
						.bookid(m.getBook())
						.count(m.getCount())
						.build());
		return cartitemDtoList;
	}

}
