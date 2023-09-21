package com.example.boardproject.comment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	
	private Long commentId;
	private String comment;
	private LocalDateTime date;
	private Long memberId;
	private Long boardId;

}
