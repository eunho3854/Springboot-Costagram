package com.cos.costagram.domain.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 30, unique = true)
	private String username;
	
	// json으로 파싱 안 되게 막음.
	@JsonIgnore
	private String password;
	
	private String name;  // 이름
	
	private String webSite; // 자기 홈페이지
	
	private String bio; // 자기소개
	
	private String email;
	
	private String phone;
	
	private String gender;
	
	private String profileImage;
	
	private String provider; // 제공자 Google, Facebook, Naver
	
	@Enumerated(EnumType.STRING)
	private RoleType role; // USER, ADMIN
	
	@CreationTimestamp
	private Timestamp createDate;
}
