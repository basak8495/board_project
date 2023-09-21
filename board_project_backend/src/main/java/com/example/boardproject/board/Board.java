package com.example.boardproject.board;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	
	private Long boardId;
	private String title;
	private String content;
	private LocalDateTime date;
	private Long viewCount;
	private Long memberId;

}