package com.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartitemRequestDTO {
	private String bookid;
	
	private int count;
	
}
