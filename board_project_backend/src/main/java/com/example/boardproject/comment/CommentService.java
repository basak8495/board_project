package com.example.boardproject.comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
	
	@Autowired
	private CommentDao commentDao;
	
	// 특정 게시물의 댓글 리스트 불러오기
	public List<Comment> findCommentListById(Long boardId) {
		
		return commentDao.findCommentListById(boardId);
	}
	
	// 댓글 작성
	public void addComment(CommentRequestDto commentRequestDto) {
		
		LocalDateTime date = LocalDateTime.now();
		
		// 원하는 형식을 지정하는 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // LocalDateTime 객체를 문자열로 변환하여 출력
        String formattedDate = date.format(formatter);
        // 문자열을 LocalDateTime 객체로 파싱
        LocalDateTime parsedDate = LocalDateTime.parse(formattedDate, formatter);
        
        commentRequestDto.setDate(parsedDate);
        
        Comment comment = commentRequestDto.dtoToEntity();
        
        commentDao.addComment(comment);
	}
	
	// 댓글 삭제
	public void removeCommentById(Long commentId) {
		commentDao.removeCommentById(commentId);
	}

}
