package com.example.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToOrderDTO {
	
	//책정보+카트아이템(cartitemid, count) 합친 리스트 
		private List<BooksCountsDTO> booksWCount;
		
		private String name;
		
		private String buyer;
		
		private String address;
		
		private String addDetail;
		
		private int point;
		
		private int totalprice;
		
		private String phone;
		
		private String email;

}
