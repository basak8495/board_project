package com.example.boardproject.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.boardproject.member_role.Role;
import com.example.boardproject.member_role.RoleDao;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
    private MemberDao memberDao; // MemberDao를 주입받음
	
	@Autowired
	private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberDao.findByLoginId(loginId);
        Role role = roleDao.findByMemberId(member.getMemberId());
        
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getAuthority());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        return new CustomUserDetails(member.getLoginId(), member.getPassword(), authorities);
    }

}