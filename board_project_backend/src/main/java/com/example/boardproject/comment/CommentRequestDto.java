package com.example.boardproject.comment;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
	
	private String comment;
	private LocalDateTime date;
	private Long memberId;
	private Long boardId;
	
	public Comment dtoToEntity() {
		Comment comment = Comment.builder()
				.comment(this.comment)
				.date(date)
				.memberId(memberId)
				.boardId(boardId)
				.build();
		return comment;
	}
	
}