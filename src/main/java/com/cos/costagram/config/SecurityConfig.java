package com.cos.costagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	// 모델 : Image, User, Likes, Follow, Tag : 인증 필요함.
	// auth 주소 : 인증 필요없음.
	// static 폴더
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		// http.cors().disable(); // 컨트롤러에 CrossOrigin을 붙여도, 시큐리티에서 막힘
		http.authorizeRequests()
		.antMatchers("/", "/user/**", "/image/**", "/follow/**", "/comment/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.anyRequest()
		.permitAll() // auth 경로는 다 허용
		.and()
		.formLogin()
		.loginPage("/auth/loginForm")
		.loginProcessingUrl("/login") // post /login 주소를 디스패처 확인하면 필터가 낚아챔
		.defaultSuccessUrl("/");
		// OAuth2.0과 CORS는 나중에 !!
	}
}