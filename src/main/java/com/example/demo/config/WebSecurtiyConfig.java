//서블릿 컨테이너에 서블릿 필터를 사용하라고 알려주는 설정 작업
package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.security.JwtAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Slf4j

public class WebSecurtiyConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	//HttpSecurity는 시큐리티 설정을 위한 오브젝트
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http 시큐리티 빌더
		http.cors() //WebMvConfig에서 이미 설정했으므로 기본 cors 설정
			.and()
			.csrf() //csrf는 현재 사용하지 않으므로 disable
				.disable()
			.httpBasic() //token울 사용하므로 basic 인증 disable
				.disable()
			.sessionManagement() //session 기반이 아님을 선언
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests() // /와 /auth/** 경로는 안증 안해도 됨
				.antMatchers("/", "/auth/**").permitAll()
			.anyRequest() // /와 /auth/** 이외의 모든 경로는 인증해야됨
				.authenticated();
		//filter 등록
		//매 요청마다
		//CorsFilter 실행 후
		//jwtAuthenticationFilter 실행
		http.addFilterAfter(
			jwtAuthenticationFilter,
			CorsFilter.class
		);
	}
}
