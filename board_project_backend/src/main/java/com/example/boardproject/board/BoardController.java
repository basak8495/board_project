package com.example.boardproject.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UploadService uploadService;
	
	// 게시글 리스트 불러오기
	@GetMapping("/board")
	public ResponseEntity<List<Board>> getBoardList() {
		try {
			List<Board> boardList = boardService.findBoardList();
			return ResponseEntity.ok(boardList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 특정 게시글 불러오기
	@GetMapping("/board/{boardId}")
	public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable Long boardId) {
	    try {
	        BoardResponseDto boardResponseDto = boardService.findBoardById(boardId);
	        if (boardResponseDto != null) {
	            return ResponseEntity.ok(boardResponseDto);
	        } else {
	            return ResponseEntity.notFound().build(); // 해당 ID의 게시글을 찾지 못한 경우 404 에러 반환
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	// 게시글 작성
	@PostMapping("/board")
	public ResponseEntity<String> postBoard(@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("memberId") Long memberId, @RequestParam("fileName") String fileName) {
		try {
			Long boardId = boardService.addBoard(title, content, memberId);
			
			uploadService.modifyFileById(fileName, boardId);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 게시글 수정
	@PutMapping("/board/{boardId}")
	public ResponseEntity<String> putBoardById(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
		try {
			boardService.modifyBoardById(boardId, boardRequestDto);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 게시글 삭제
	@DeleteMapping("/board/{boardId}")
	public ResponseEntity<String> deleteBoardById(@PathVariable Long boardId) {
		try {
			boardService.removeBoardById(boardId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
}
