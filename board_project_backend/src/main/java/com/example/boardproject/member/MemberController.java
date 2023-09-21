package com.example.boardproject.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.boardproject.authentication.JwtTokenProvider;
import com.example.boardproject.member_role.Role;
import com.example.boardproject.member_role.RoleDao;

@Controller
@RequestMapping("/api")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
    private MemberDao memberDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<String> postSignup(@RequestBody MemberRequestDto memberRequestDto) {
		try {
			memberService.addMember(memberRequestDto);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 로그인
	@PostMapping("/login")
    public ResponseEntity<String> postLogin(@RequestBody LoginRequestDto loginRequestDto) {
        try {
        	Member member = memberDao.findByLoginId(loginRequestDto.getLoginId());
            Role role = roleDao.findByMemberId(member.getMemberId());
            
            String authority = role.getAuthority();
            List<String> authorities = new ArrayList<>();
            authorities.add(authority);
            
            if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
                throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            }
            
            String jwtToken = jwtTokenProvider.createToken(member.getLoginId(), member.getMemberId(), member.getNickname(), authorities);
            
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
        	//e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인에 실패했습니다.(백)");
        }
    }
	
	// 내정보 불러오기
	@GetMapping("/myinfo/{memberId}")
	public ResponseEntity<MemberResponseDto> getMyinfo(@PathVariable Long memberId) {
		try {
			MemberResponseDto memberResponseDto = memberService.findMemberById(memberId);
			if (memberResponseDto != null) {
				return ResponseEntity.ok(memberResponseDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	// 내정보 수정
	@PutMapping("/myinfo/{memberId}")
	public ResponseEntity<String> putMyinfoById(@PathVariable Long memberId, @RequestBody MemberRequestDto memberRequestDto) {
		try {
			memberService.modifyMemberById(memberId, memberRequestDto);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 회원탈퇴
	@DeleteMapping("/myinfo/{memberId}")
	public ResponseEntity<String> deleteMemberById(@PathVariable Long memberId) {
		try {
			memberService.removeMemberById(memberId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}