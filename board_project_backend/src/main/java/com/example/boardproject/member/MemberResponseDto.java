package com.example.boardproject.member;

import lombok.Getter;

@Getter
public class MemberResponseDto {
	
	private Long memberId;
	private String loginId;
	private String password;
	private String nickname;
	private String name;
	private String email;
	private String phoneNumber;

}
