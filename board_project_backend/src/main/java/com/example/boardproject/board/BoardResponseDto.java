package com.example.boardproject.board;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class BoardResponseDto {
	
	private Long boardId;
	private String title;
	private String content;
	private LocalDateTime date;
	private Long viewCount;
	private Long memberId;
	
	private String nickname;

}
