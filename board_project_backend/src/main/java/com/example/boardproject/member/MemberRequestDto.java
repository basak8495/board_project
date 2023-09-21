package com.example.boardproject.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {

	private String loginId;
	private String password;
	private String nickname;
	private String name;
	private String email;
	private String phoneNumber;
	
	public Member dtoToEntity() {
		Member member = Member.builder()
				.loginId(loginId)
				.password(password)
				.nickname(nickname)
				.name(name)
				.email(email)
				.phoneNumber(phoneNumber)
				.build();
		return member;
	}
	
}
