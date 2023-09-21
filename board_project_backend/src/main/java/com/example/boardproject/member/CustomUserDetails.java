package com.example.boardproject.member;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private String loginId;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private static final long serialVersionUID = 1L;

    // UserDetails 인터페이스의 다른 메서드 구현

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true; // 예제에서는 계정 만료 여부를 체크하지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 예제에서는 계정 잠김 여부를 체크하지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 예제에서는 자격증명 만료 여부를 체크하지 않음
    }

    @Override
    public boolean isEnabled() {
        return true; // 예제에서는 계정 활성 여부를 체크하지 않음
    }

}
