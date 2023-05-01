package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Member;
import com.example.domain.Qna;
import com.example.repository.QnaRepository;

@Service
public class QnaService {

	@Autowired
	private QnaRepository qnaRepository;
	
	public Qna save(Qna newQna) {
		
		return qnaRepository.save(newQna);
	}




	public List<Qna> findByWriter(Member findWriter) {
		
		return qnaRepository.findByWriter(findWriter);
	}




	public List<Qna> findAll() {

		return qnaRepository.findAll();
	}




	public Qna findById(int id) {
		
		return qnaRepository.findById(id).get();
	}

}
