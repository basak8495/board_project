package com.example.boardproject.comment;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 특정 게시물의 댓글 리스트 불러오기
	public List<Comment> findCommentListById(Long boardId) {
		
		return sqlSession.selectList("com.example.boardproject.comment.CommentDao.selectCommentList", boardId);
	}
	
	// 댓글 작성
	public void addComment(Comment comment) {
		sqlSession.insert("com.example.boardproject.comment.CommentDao.insertComment", comment);
	}
	
	// 댓글 삭제
	public void removeCommentById(Long commentId) {
		sqlSession.delete("com.example.boardproject.comment.CommentDao.deleteComment", commentId);
	}
}
