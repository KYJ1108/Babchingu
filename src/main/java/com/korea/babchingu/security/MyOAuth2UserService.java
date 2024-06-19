package com.korea.babchingu.security;


import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MyOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User user = super.loadUser(userRequest);

        // 계정 -> sub
        // 비밀번호 -> ""
        // 닉네임 -> name
        // email -> email

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        MySocialUser mySocialUser;

        switch (registrationId) {
            case "google" -> mySocialUser = googleService(user);
            case "kakao" -> mySocialUser = kakaoService(user);
            case "naver" -> mySocialUser = naverService(user);
            default -> throw new IllegalStateException("Unexpected value: " + registrationId);
        }

        Member member = memberRepository.findByLoginId(mySocialUser.getSub()).orElse(null);

        if (member == null) {
            member = new Member();
            member.setLoginId(mySocialUser.getSub());
            member.setPassword(mySocialUser.getPass());
            member.setNickname(mySocialUser.getName());
            member.setEmail(mySocialUser.getEmail());
            member.setCreateDate(LocalDateTime.now());
            member.setUpdateDate(LocalDateTime.now());

            memberRepository.save(member);
        }

        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                user.getAttributes(),
                "sub");
    }

    public MySocialUser googleService(OAuth2User user) {
        String sub = user.getAttribute("sub");
        String pass = "";
        String name = user.getAttribute("name");
        String email = user.getAttribute("email");

        return new MySocialUser(sub, pass, name, email);
    }

    public MySocialUser kakaoService(OAuth2User user) {
        String sub = user.getAttribute("id").toString();
        String pass = "";

        Map<String, Object> kakaoAccount = user.getAttribute("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String name = (String) profile.get("nickname");

        String email = (String) kakaoAccount.get("email");

        return new MySocialUser(sub, pass, name, email);
    }

    public MySocialUser naverService(OAuth2User user) {
        Map<String, Object> response = user.getAttribute("response");
        String sub = response.get("id").toString();
        String pass = "";
        String name = response.get("name").toString();
        String email = response.get("email").toString();

        return new MySocialUser(sub, pass, name, email);
    }
}