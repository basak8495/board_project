package com.example.boardproject.board;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
	
	private String title;
	private String content;
	private LocalDateTime date;
	private Long viewCount;
	private Long memberId;
	
	public Board dtoToEntity() {
		Board board = Board.builder()
				.title(title)
				.content(content)
				.date(date)
				.viewCount(viewCount)
				.memberId(memberId)
				.build();
		return board;
	}

}