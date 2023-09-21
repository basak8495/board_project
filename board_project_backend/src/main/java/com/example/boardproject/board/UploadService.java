package com.example.boardproject.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadService {
	
	@Autowired
	private FileDao fileDao;
	
	// 파일 업로드
	public void addFile(FileRequestDto fileRequestDto) {
		
		File file = fileRequestDto.dtoToEntity();
		
		fileDao.addFile(file);
	}
	
	// 업로드 파일 수정 (boardId 추가)
	public void modifyFileById(String fileName, Long boardId) {
		fileDao.modifyFileById(fileName, boardId);
	}
	
	// 업로드 파일 삭제
	public void removeFile(String fileName) {
		fileDao.removeFile(fileName);
	}
}