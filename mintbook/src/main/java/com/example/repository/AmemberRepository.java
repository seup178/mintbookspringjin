package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Member;

public interface AmemberRepository extends JpaRepository<Member, Integer> {

}
