package com.example.boardproject.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.boardproject.member_role.MemberRoleDao;

@Service
public class MemberService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private MemberRoleDao memberRoleDao;
	
	// 회원가입
	public void addMember(MemberRequestDto memberRequestDto) {
		
		String encodedPassword = passwordEncoder.encode(memberRequestDto.getPassword());
		
		memberRequestDto.setPassword(encodedPassword);
		
		Member member = memberRequestDto.dtoToEntity();
		
		Member registeredMember = memberDao.registerMember(member);
		
		Long memberId = registeredMember.getMemberId();
		Long roleId = 2l;
		
		memberRoleDao.addMemberRole(memberId, roleId);
	}
	
	// 내정보 불러오기
	public MemberResponseDto findMemberById(Long memberId) {
		
		return memberDao.findMemeberById(memberId);
	}
	
	// 내정보 수정
	public void modifyMemberById(Long memberId, MemberRequestDto memberRequestDto) {
		memberDao.modifyMemberById(memberId, memberRequestDto);
	}
	
	// 회원탈퇴
	public void removeMemberById(Long memberId) {
		memberDao.removeMemberById(memberId);
	}

}