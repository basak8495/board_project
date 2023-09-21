package com.example.boardproject.member_role;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public Role findByMemberId(Long memberId) {
		return sqlSession.selectOne("com.example.boardproject.member_role.RoleDao.findRolesByMemberId", memberId);
	}

}
