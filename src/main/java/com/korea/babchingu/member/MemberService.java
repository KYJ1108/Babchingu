package com.korea.babchingu.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member save(String loginId, String password, String email) {
        // 아이디 중복 체크
        if (memberRepository.findByLoginId(loginId).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        // 이메일 중복 체크 (필요시 추가)
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(passwordEncoder.encode(password));
        member.setEmail(email);

        return memberRepository.save(member);
    }
}