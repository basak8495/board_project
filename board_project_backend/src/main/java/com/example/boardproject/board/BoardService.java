package com.example.boardproject.board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	// 게시글 리스트 불러오기
	public List<Board> findBoardList() {
		
		return boardDao.findBoardList();
	}
	
	// 특정 게시글 불러오기
	public BoardResponseDto findBoardById(Long boardId) {
		
		return boardDao.findBoardById(boardId);
	}
	
	// 게시글 작성
	public Long addBoard(String title, String content, Long memberId) {
		
		LocalDateTime date = LocalDateTime.now();
		
		// 원하는 형식을 지정하는 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // LocalDateTime 객체를 문자열로 변환하여 출력
        String formattedDate = date.format(formatter);
        // 문자열을 LocalDateTime 객체로 파싱
        LocalDateTime parsedDate = LocalDateTime.parse(formattedDate, formatter);
		
        BoardRequestDto boardRequestDto = new BoardRequestDto();
        boardRequestDto.setTitle(title);
        boardRequestDto.setContent(content);
        boardRequestDto.setDate(parsedDate);
		boardRequestDto.setViewCount(0L);
		boardRequestDto.setMemberId(memberId);
		
		Board board = boardRequestDto.dtoToEntity();
		
		return boardDao.addBoard(board);
		
	}
	
	// 게시글 수정
	public void modifyBoardById(Long boardId, BoardRequestDto boardRequestDto) {
		boardDao.modifyBoardById(boardId, boardRequestDto);
	}
	
	// 게시글 삭제
	public void removeBoardById(Long boardId) {
		boardDao.removeBoardById(boardId);
	}
	
}