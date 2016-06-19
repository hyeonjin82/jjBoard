package com.jjboard.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jjboard.mvc.domain.Board;

public interface BoardDao extends JpaRepository<Board, Integer> {
	
} 