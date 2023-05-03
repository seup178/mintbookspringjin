package com.example.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Qna {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 4000)
	private String content;
	
	@Column(length = 200)
	private String qnaTitle;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "writer")
	private Member writer;
	
	
	private LocalDate reg_date=LocalDate.now();
	
	@Column(length = 4000)
	private String reply;

	public void update(String reply) {
		this.reply = reply;
		
	}

	
	public void updateTitleContent(String qnaTitle, String content) {
		this.qnaTitle = qnaTitle;
		this.content = content;
		
	}


	
	
}
