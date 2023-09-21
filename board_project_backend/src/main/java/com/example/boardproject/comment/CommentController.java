package com.example.boardproject.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	// 특정 게시글의 댓글 리스트 불러오기
	@GetMapping("/comment/{boardId}")
	public ResponseEntity<List<Comment>> getCommentListById(@PathVariable Long boardId) {
		try {
			List<Comment> commentList = commentService.findCommentListById(boardId);
			return ResponseEntity.ok(commentList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 댓글 작성
	@PostMapping("/comment")
	public ResponseEntity<String> postComment(@RequestBody CommentRequestDto commentRequestDto) {
		try {
			commentService.addComment(commentRequestDto);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 댓글 삭제
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<String> deleteCommentById(@PathVariable Long commentId) {
		try {
			commentService.removeCommentById(commentId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
