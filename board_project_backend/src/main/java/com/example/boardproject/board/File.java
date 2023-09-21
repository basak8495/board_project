package com.example.boardproject.board;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {

	private Long fileId;
	private String path;
	private String uuid;
	private String originalName;
	private String fileName;
	private Long boardId;
	
	List<File> fileList;
}
