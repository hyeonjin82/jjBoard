package com.jjboard.mvc.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Entity(name="board")
@Data
public class Board {
	
	@Id  @GeneratedValue
	private int  seq;
	@Length(min=2, message="must be over 2 characters!")
	private String title;
	@NotEmpty(message = "Please input the content.")
	private String content;
	@NotEmpty(message = "Please input the writer")
	private String writer;
	private int password;
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	private int cnt;
	
	public Board() {	}
	
	public Board(String title, String content, String writer, int password) {
		super();
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.password = password;
		this.cnt = 0;
	}
}
