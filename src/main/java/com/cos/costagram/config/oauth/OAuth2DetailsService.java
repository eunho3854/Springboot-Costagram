package com.cos.costagram.config.oauth;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.costagram.config.auth.PrincipalDetails;
import com.cos.costagram.domain.user.RoleType;
import com.cos.costagram.domain.user.User;
import com.cos.costagram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {
	
	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		return processOAuth2User(userRequest, oAuth2User);
	}
	
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {		
		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getClientName().equals("Facebook")) {
			oAuth2UserInfo = new FacebookInfo(oAuth2User.getAttributes());
		}
		
		User userEntity = userRepository.findByUsername(oAuth2UserInfo.getUsername());
		
		UUID uuid = UUID.randomUUID() ;
		String enPassword = new BCryptPasswordEncoder().encode(uuid.toString()); 
		
		if(userEntity == null) {
			User user = User.builder()
					.username(oAuth2UserInfo.getUsername())
					.password(enPassword)
					.email(oAuth2UserInfo.getEmail())
					.role(RoleType.USER)
					.build();
			userEntity = userRepository.save(user);
			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		} else {
			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		}
	}

}
