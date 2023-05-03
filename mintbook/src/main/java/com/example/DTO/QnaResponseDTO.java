package com.example.DTO;

import java.time.LocalDate;

import com.example.domain.Member;

import lombok.Data;

@Data
public class QnaResponseDTO {
	private int id;
	private String content;
	private String qnaTitle;
	private String writer;
	private LocalDate reg_date;
	private String reply;
}
