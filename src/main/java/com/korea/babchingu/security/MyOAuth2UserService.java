package com.korea.babchingu.security;


import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MyOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User user = super.loadUser(userRequest);

        String sub = user.getAttribute("sub");
        String pass = "";
        String name = user.getAttribute("name");
//        String email = user.getAttribute("email");

        Member member = memberRepository.findByLoginId(sub).orElse(null);

        if(member == null) {

            member = new Member();
            member.setLoginId(sub);
            member.setPassword(pass);
            member.setNickname(name);
//            member.setEmail(email);
//            member.setCreateDate(LocalDateTime.now());

            memberRepository.save(member);
        }

        return super.loadUser(userRequest);
    }
}

