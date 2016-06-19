package com.jjboard.mvc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.jjboard.mvc.domain.Board;
import com.jjboard.mvc.service.BoardService;

import scala.annotation.varargs;

@Controller
@SessionAttributes("board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	public BoardService getBoardService() {
		return boardService;
	}

	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@RequestMapping(value="/")
	public String delist(Model model) {
		model.addAttribute("boardList", boardService.list());
		return "/board/list";
	}
	
	@RequestMapping(value="/board/list")
	public String list(Model model) {
		model.addAttribute("boardList", boardService.list());
		return "/board/list";
	}
	
	@RequestMapping(value = "/board/read/{seq}")
		public String read(Model model, @PathVariable int seq) {
			model.addAttribute("board", boardService.read(seq));
			return "/board/read";
		}
	
		@RequestMapping(value = "/board/write", method = RequestMethod.GET)
		public String write(Model model) {
			model.addAttribute("board", new Board());
			return "/board/write";
		}
	
		@RequestMapping(value = "/board/write", method = RequestMethod.POST)
		public String write(@Valid Board Board, BindingResult bindingResult, SessionStatus sessionStatus) {
			if (bindingResult.hasErrors()) {
				return "/board/write";
			} else {
				boardService.write(Board);
				sessionStatus.setComplete();
				return "redirect:/board/list";
			}
		}
	
		@RequestMapping(value = "/board/edit", method = RequestMethod.GET)
		public String edit(@ModelAttribute Board Board) {
			return "/board/edit";
		}
	
		@RequestMapping(value = "/board/edit", method = RequestMethod.POST)
		public String edit(@Valid @ModelAttribute Board Board,
				BindingResult result, int pwd, SessionStatus sessionStatus,
				Model model) {
			if (result.hasErrors()) {
				return "/board/edit";
			} else {
				if (Board.getPassword() == pwd) {
					boardService.edit(Board);
					sessionStatus.setComplete();
					return "redirect:/board/list";
				}
	
				model.addAttribute("msg", "not match the password.");
				return "/board/edit";
			}
		}
	
		@RequestMapping(value = "/board/delete/{seq}", method = RequestMethod.GET)
		public String delete(@PathVariable int seq, Model model) {
			model.addAttribute("seq", seq);
			return "/board/delete";
		}
	
		@RequestMapping(value = "/board/delete", method = RequestMethod.POST)
		public String delete(int seq, int pwd, Model model) {
			Board board = new Board();
			board.setSeq(seq);
			board.setPassword(pwd);
	
			boolean deleted = boardService.delete(board);
	
			if (deleted == false) {
				model.addAttribute("seq", seq);
				model.addAttribute("msg", "not match the password.");
				return "/board/delete";
			} else {
				return "redirect:/board/list";
			}
		}
}