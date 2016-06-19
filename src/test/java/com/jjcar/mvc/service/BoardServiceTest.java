package com.jjcar.mvc.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jjboard.mvc.dao.BoardDao;
import com.jjboard.mvc.domain.Board;
import com.jjboard.mvc.service.BoardServiceImpl;


public class BoardServiceTest {
	
	List<Board> boards;
	BoardServiceImpl boardServiceImpl;
	BoardDao mockBoardDao;

	@Before
	public void setUp() {
		this.boards = new ArrayList<Board>();
		
		Board Board;
		
		Board = new Board("Title1", "Content1", "Writer1", 1000);
		Board.setSeq(1);
		this.boards.add(Board);
		
		Board = new Board("Title2", "Content2", "Writer2", 2000);
		Board.setSeq(2);
		this.boards.add(Board);
		
		Board = new Board("Title3", "Content3", "Writer3", 3000);
		Board.setSeq(3);
		this.boards.add(Board);
		
		this.mockBoardDao = mock(BoardDao.class);
		when(this.mockBoardDao.findAll()).thenReturn(this.boards);
		when(this.mockBoardDao.findOne(1)).thenReturn(this.boards.get(0));
		when(this.mockBoardDao.findOne(2)).thenReturn(this.boards.get(1));
		when(this.mockBoardDao.findOne(3)).thenReturn(this.boards.get(2));
		
		this.boardServiceImpl = new BoardServiceImpl();
		this.boardServiceImpl.setBoardDao(mockBoardDao);
		
	}

	@Test
	public void testList() {
		List<Board> boardList = boardServiceImpl.list();
		
		assertThat(boardList.size(), is(3));
		
	}
	
	@Test
	public void testRead(){
		Board Board;
		
		for (int index = 0; index < boards.size(); index++) {
			Board = boardServiceImpl.read((index + 1));
			compareVO(Board, index);
		}
	}

	private void compareVO(Board Board, int index) {
		assertThat(Board.getTitle(), is(this.boards.get(index).getTitle()));
		assertThat(Board.getContent(), is(this.boards.get(index).getContent()));
		assertThat(Board.getWriter(), is(this.boards.get(index).getWriter()));
		assertThat(Board.getCnt(), is(this.boards.get(index).getCnt()));
	}

}
