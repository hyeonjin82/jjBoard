package com.jjboard.mvc.service;

import java.util.List;

import com.jjboard.mvc.domain.Board;

public interface BoardService {
	public abstract List<Board> list();
	
	public abstract boolean  delete (Board board);
	
	public abstract Board  edit (Board board);
	
	public abstract Board write(Board board);
	
	public abstract Board read(int  seq);
}
