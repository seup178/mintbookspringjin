package com.example.DTO;

import java.time.LocalDateTime;

import com.example.domain.Book;
import com.example.domain.Editorpick;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditorResponseDTO {
	private int id;
	private Book bookid;
	private LocalDateTime regDate;
	private String content;
	private String title;
	
	public EditorResponseDTO(Editorpick entity) {
		this.id = entity.getId();
		this.bookid = entity.getBookid();
		this.regDate = entity.getRegDate();
		this.content = entity.getContent();
		this.title = entity.getTitle();
	}



}
