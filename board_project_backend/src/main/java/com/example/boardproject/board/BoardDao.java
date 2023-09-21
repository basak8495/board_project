package com.example.boardproject.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 게시글 리스트 불러오기
	public List<Board> findBoardList() {
		
		return sqlSession.selectList("com.example.boardproject.board.BoardDao.selectBoardList");
	}
	
	// 특정 게시글 불러오기
	public BoardResponseDto findBoardById(Long boardId) {
		sqlSession.update("com.example.boardproject.board.BoardDao.updateBoardViewCount", boardId);
		return sqlSession.selectOne("com.example.boardproject.board.BoardDao.selectBoard", boardId);
	}
	
	// 게시글 작성
	public Long addBoard(Board board) {
		sqlSession.insert("com.example.boardproject.board.BoardDao.insertBoard", board);
		Long boardId = board.getBoardId();
		return boardId;
	}
	
	// 게시글 수정
	public void modifyBoardById(Long boardId, BoardRequestDto boardRequestDto) {
		Map<String, Object> params = new HashMap<>();
	    params.put("boardId", boardId);
	    params.put("boardRequestDto", boardRequestDto);
		sqlSession.update("com.example.boardproject.board.BoardDao.updateBoard", params);
	}
	
	// 게시글 삭제
	public void removeBoardById(Long boardId) {
		sqlSession.delete("com.example.boardproject.board.BoardDao.deleteBoard", boardId);
	}

}
