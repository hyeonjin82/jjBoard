package com.jjcar.mvc.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

import com.jjboard.mvc.JjboardApplication;
import com.jjboard.mvc.controller.BoardController;
import com.jjboard.mvc.domain.Board;
import com.jjboard.mvc.service.BoardService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JjboardApplication.class)
public class BoardContollerTest {

	@Test
	public void postWriteOK() {
		BoardController controller = new BoardController();
		BoardService service = mock(BoardService.class);
		SessionStatus sessionStatus = mock(SessionStatus.class);
		controller.setBoardService(service);

		Board Board = new Board();
		Board.setTitle("spring");

		BindingResult result = new BeanPropertyBindingResult(Board, "board");

		// Model model = new ExtendedModelMap();
		String viewName = controller.write(Board, result, sessionStatus);
		// 문제점 BindingResult 에 강제 에러를 설정해 주지 않는 이상 필수 항목 값을 다 입력하지 않아도 통과
		assertThat(viewName, is("redirect:/board/list"));
		// service 의 메서드 중에 Board 를 인자로하는 write() 메서드가 호출된 적이 있는지 검사
		verify(service).write(Board);

	}

	@Test
	public void postWriteError() {
		BoardController controller = new BoardController();
		BoardService service = mock(BoardService.class);
		SessionStatus sessionStatus = mock(SessionStatus.class);	
		controller.setBoardService(service);

		Board Board = new Board();
		Board.setTitle("spring");

		BindingResult result = new BeanPropertyBindingResult(Board, "board");
		result.rejectValue("content", "required", "input context please");

		// Model model = new ExtendedModelMap();

		String viewName = controller.write(Board, result, sessionStatus);
		assertThat(viewName, is("/board/write")); 
		// verify(service).write(Board);
	}

}
