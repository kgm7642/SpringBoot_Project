package com.project.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class Users implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private String usersnumber; //DB에서 PK 값
	private String usersid; // 로그인용 ID 값
	private String userspw; // 비밀번호
	private String usersnickname; // 닉네임
	private String usersemail; // 이메일
	private String auth; // 유저 역할
	private String createdate; // 계정 생성 날짜
	private String modifydate; // 계정 수정 날짜
	
    /**
    * 해당 유저의 권한 목록
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
          roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

	/**
    * 비밀번호
    */
	@Override
    public String getPassword() {
        return userspw;
    }


	/**
    * PK값
    */
    @Override
    public String getUsername() {
        return usersnumber;
    }

    /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 활성화 여부
     * ture : 활성화
     * false : 비활성화
     * @return
     */
    @Override
    public boolean isEnabled() {
        //이메일이 인증되어 있고 계정이 잠겨있지 않으면 true
        return true;
    }
}