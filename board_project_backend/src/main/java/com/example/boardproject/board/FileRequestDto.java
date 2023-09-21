package com.example.boardproject.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRequestDto {
	
	private String path;
	private String uuid;
	private String originalName;
	private String fileName;
	private Long boardId;
	
	public File dtoToEntity() {
		File file = File.builder()
				.path(path)
				.uuid(uuid)
				.originalName(originalName)
				.fileName(fileName)
				.boardId(boardId)
				.build();
		return file;
	}

}
