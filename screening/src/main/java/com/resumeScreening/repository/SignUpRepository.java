package com.resumeScreening.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resumeScreening.model.SignUpTable;

public interface SignUpRepository extends JpaRepository<SignUpTable, Long> {
	public Optional<SignUpTable> findByEmail(String email);
}

