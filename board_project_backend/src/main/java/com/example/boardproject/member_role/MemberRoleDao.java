package com.example.boardproject.member_role;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRoleDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 회원가입 권한 설정
	public void addMemberRole(Long memberId, Long roleId) {
		Map<String, Object> memberRole = new HashMap<>();
		memberRole.put("memberId", memberId);
		memberRole.put("roleId", roleId);
		
		sqlSession.insert("com.example.boardproject.member_role.MemberRoleDao.insertMemberRole", memberRole);
	}

}