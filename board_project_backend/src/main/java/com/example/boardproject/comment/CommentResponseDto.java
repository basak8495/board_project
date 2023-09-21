package com.example.boardproject.comment;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CommentResponseDto {
	
	private Long commentId;
	private String comment;
	private LocalDateTime date;
	private Long memberId;
	private Long boardId;
	
	private String nickname;

}
