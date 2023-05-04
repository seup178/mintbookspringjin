package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Member;
import com.example.domain.Qna;

public interface QnaRepository extends JpaRepository<Qna, Integer> {

	List<Qna> findByWriter(Member findWriter);

	List<Qna> findByWriterIn(List<Member> searchEmail);

}
