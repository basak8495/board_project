package com.example.boardproject.member;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 회원가입
	public Member registerMember(Member member) {
		sqlSession.insert("com.example.boardproject.member.MemberDao.insertMember", member);
		return sqlSession.selectOne("com.example.boardproject.member.MemberDao.selectMemberById", member.getLoginId());
	}
	
	// 로그인
	public Member findByLoginId(String loginId) {
		
		return sqlSession.selectOne("com.example.boardproject.member.MemberDao.selectMemberById", loginId);
	}
	
	// 내정보 불러오기
	public MemberResponseDto findMemeberById(Long memberId) {
		
		return sqlSession.selectOne("com.example.boardproject.member.MemberDao.selectMember", memberId);
	}
	
	// 내정보 수정
	public void modifyMemberById(Long memberId, MemberRequestDto memberRequestDto) {
		Map<String, Object> params = new HashMap<>();
		params.put("memberId", memberId);
		params.put("memberRequestDto", memberRequestDto);
		sqlSession.update("com.example.boardproject.member.MemberDao.updateMember", params);
	}
	
	// 회원탈퇴
	public void removeMemberById(Long memberId) {
		sqlSession.delete("com.example.boardproject.member.MemberDao.deleteMember", memberId);
	}
}