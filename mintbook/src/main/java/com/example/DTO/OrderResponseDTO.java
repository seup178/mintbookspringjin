package com.example.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
	
	private List<BooksCountsDTO> booksWCount;
	private String memberName;
	private String buyer;
	private String address;
	private String phone;
	private String orderNum;
	private LocalDateTime orderDate;
	private LocalDateTime arrivalDate;
	private String status;
	private int totalprice;
	private int finalprice;
	private String payMethod;
	
	

}
