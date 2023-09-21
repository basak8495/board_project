package com.example.boardproject.board;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 파일 업로드
	public void addFile(File file) {
		sqlSession.insert("com.example.boardproject.board.FileDao.insertFile", file);
	}
	
	// 업로드 파일 수정 (boardId 추가)
	public void modifyFileById(String fileName, Long boardId) {
		Map<String, Object> params = new HashMap<>();
		params.put("fileName", fileName);
		params.put("boardId", boardId);
		sqlSession.update("com.example.boardproject.board.FileDao.updateFile", params);
	}

	// 업로드 파일 삭제
	public void removeFile(String fileName) {
		sqlSession.delete("com.example.boardproject.board.FileDao.deleteFile", fileName);
	}
}
