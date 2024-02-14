package com.resumeScreening.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resumeScreening.model.SignUpTable;

public interface SignUpRepository extends JpaRepository<SignUpTable, Long> {

}

