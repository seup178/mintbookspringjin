package com.example.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AMemberResponseDTO {

	private int id;
	
	private String email;
	
	private String name;
	
	private LocalDate birth;//생일
}
