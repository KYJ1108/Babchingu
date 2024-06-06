package com.korea.babchingu.security;

import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    public final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByLoginId(username).orElseThrow(
                () -> new RuntimeException("존재하지 않는 아이디입니다.")
        );
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        List<SimpleGrantedAuthority> authorities = List.of(authority);

//        if(username.equals("youshin")) {
//            authority = new SimpleGrantedAuthority("ADMIN");
//            authorities.add(authority);
//        }

        return new User(member.getLoginId(), member.getPassword(), authorities);
    }
}
