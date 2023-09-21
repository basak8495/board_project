package com.example.boardproject.board;

import lombok.Getter;

@Getter
public class FileResponseDto {
	
	private Long fileId;
	private String path;
	private String uuid;
	private String originalName;
	private String fileName;
	private Long boardId;

}
