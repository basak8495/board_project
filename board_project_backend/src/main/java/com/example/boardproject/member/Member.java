package com.example.boardproject.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	private Long memberId;
	private String loginId;
	private String password;
	private String nickname;
	private String name;
	private String email;
	private String phoneNumber;

}