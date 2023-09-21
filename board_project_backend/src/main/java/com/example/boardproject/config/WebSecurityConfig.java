package com.example.boardproject.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.boardproject.authentication.JwtAuthenticationFilter;
import com.example.boardproject.authentication.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	//이거 안해줬더니 프론트에서 post로 /api/login을 보냈는데 백에서 get으로 오류가 났다.
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
			.antMatchers(); // 보안이 적용될 필요 없는 경로
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/**").permitAll() // 특정경로.누구나 접근 가능
				.anyRequest().authenticated() // 인증된 사용자만 접근 가능
				.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("login_id")
				.defaultSuccessUrl("/")
				.failureUrl("/signup")
				.permitAll()
				.and()
			.logout()
				.permitAll()
				.and()
			.cors()
				.and()
			.httpBasic().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                    UsernamePasswordAuthenticationFilter.class)
			.csrf()
				.disable();
		    	//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); //csrf 토큰 자동생성
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder())
			.usersByUsernameQuery("SELECT login_id, password "
					+ "FROM member "
					+ "WHERE login_id = ?")
			.authoritiesByUsernameQuery("SELECT m.login_id, r.authority "
					+ "FROM member_role mr "
					+ "INNER JOIN member m ON mr.member_id = m.member_id "
					+ "INNER JOIN role r ON mr.role_id = r.role_id "
					+ "WHERE m.login_id = ?");
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}