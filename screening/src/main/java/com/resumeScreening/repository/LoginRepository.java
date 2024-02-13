package com.resumeScreening.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resumeScreening.model.LoginTable;

public interface LoginRepository extends JpaRepository<LoginTable, Long>{
	
	public Optional<LoginTable> findByUserName(String userName);

	
}
