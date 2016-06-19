package com.jjboard.mvc.dao;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jjboard.mvc.JjboardApplication;
import com.jjboard.mvc.domain.Board;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JjboardApplication.class)
public class BoardDaoTest {

	@Autowired
	private BoardDao boardDao;

	private List<Board> boardList;

	@Before
	public void setUp() {
		boardDao.deleteAll();

		boardDao.save(new Board("hi jin", "hello jin", "Leehyeo", 1234));
		boardDao.save(new Board("hi mike", "hello mike", "Mike", 1234));
		boardDao.save(new Board("hi alex", "hello alex", "Alex", 1234));

		boardList = boardDao.findAll();
	}

	@Test
	public void list() {
		assertThat(boardList.size(), is(3));

		Board board;

		for (int i = 0; i < boardList.size(); i++) {
			board = boardDao.findOne(boardList.get(i).getSeq());
			 compare(board, boardList.get(i));
		}
	}

	@Test
	public void delete() {
		int seq = boardList.get(0).getSeq();
		boardDao.delete(boardList.get(0));
		boardList = boardDao.findAll();

		assertThat(boardList.size(), is(2));
		assertThat(boardDao.findOne(seq), is(nullValue()));
	}

	@Test
	public void deleteAll() {
		boardDao.deleteAll();

		boardList = boardDao.findAll();

		assertThat(boardList.size(), is(0));
	}

	@Test
	public void update() {
		Board board = boardList.get(0);

		board.setTitle("title");
		board.setContent("context");
		board.setWriter("writer");

		boardDao.save(board);

		 compare(boardDao.findOne(board.getSeq()), board);
	}

	@Test
	public void insert() {
		Board board;
		Board dbBoard;

		board = new Board("Title1", "content1", "writer1", 4343);

		int size = boardDao.findAll().size();

		boardDao.save(board);

		dbBoard = boardDao.findOne(board.getSeq());
		board.setRegDate(dbBoard.getRegDate());

		assertThat(boardDao.findAll().size(), is(size + 1));
		 compare(boardDao.findOne(board.getSeq()), board);
	}

	@Test
	public void select() {
		int index;
		Board board;
		
		index = 0;
		board = boardDao.findOne(boardList.get(index).getSeq());
		compare(board, boardList.get(index));
		
		index = 1;
		board = boardDao.findOne(boardList.get(index).getSeq());
		compare(board, boardList.get(index));
		
	}
	
	@Test
	public void updateReadCount() {
		int index;
		int readCount;
		Board board;
		Board board2;
		
		index = 0;
		int pk = boardList.get(index).getSeq();
		
		board = boardDao.findOne(pk);
		readCount = board.getCnt();
		board.setCnt(board.getCnt() + 1);
		boardDao.save(board);
		
		board2 = boardDao.findOne(pk);
		
		assertThat(board2.getCnt(), is (readCount + 1));
	}
	
	private void compare(Board b1, Board b2) {
		assertThat(b1.getSeq(), is(b2.getSeq()));
		assertThat(b1.getTitle(), is(b2.getTitle()));
		assertThat(b1.getContent(), is(b2.getContent()));
		assertThat(b1.getWriter(), is(b2.getWriter()));
		assertThat(b1.getRegDate(), is(b2.getRegDate()));
		assertThat(b1.getPassword(), is(b2.getPassword()));
		assertThat(b1.getCnt(), is(b2.getCnt()));
	}

}
