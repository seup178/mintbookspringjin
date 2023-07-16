package com.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksCountsDTO {
	private int Bookid;
	private String BookName;
	private String Author;
	private String Publisher;
	private int cartitemid;
	private int count;
	private int total;

}
