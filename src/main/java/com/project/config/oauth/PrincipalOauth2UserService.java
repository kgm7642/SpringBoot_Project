package com.project.config.oauth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.domain.PrincipalDetails;
import com.project.domain.Users;
import com.project.service.UsersService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
 
	private final UsersService usersService;
	private final HttpServletRequest httpServletRequest;
	
	// 구글 로그인 시 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
		System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());

		OAuth2User oauth2User = super.loadUser(userRequest);
//		구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인을 완료 -> code를 리턴(OAuth-Client라이브러리)
//		-> AccessToken요청 -> userRequest 정보 -> (회원프로필을 받아야함) loadUser함수 호출
//		-> 구글로부터 회원프로필 받아준다.
		System.out.println("userRequest : "+ oauth2User.getAttributes());
		
//		회원가입을 강제로 진행한다.
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String provider = userRequest.getClientRegistration().getRegistrationId(); // google
		String providerId = oauth2User.getAttribute("sub");
		String username = provider +"_"+ providerId;
		String password = encoder.encode("겟인데어");
		String email = oauth2User.getAttribute("email");
		String role = "ROLE_USER";
		Users users = usersService.getUsers(username);
		if(users == null) {
			users = new Users();
			users.setUsername(username);
			users.setUserspw(password);
			users.setUsersemail(email);
			users.setRole(role);
			users.setProvider(provider);
			users.setProviderid(providerId);
			httpServletRequest.setAttribute("users", users);
			System.out.println("구글 로그인을 처음 시도했습니다.");
		} else {
			httpServletRequest.setAttribute("users", users);
			System.out.println("이미 구글 로그인을 진행한 적이 있습니다.");
		}
		return new PrincipalDetails(users, oauth2User.getAttributes());
	}
}
