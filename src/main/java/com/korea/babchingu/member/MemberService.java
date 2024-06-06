package com.korea.babchingu.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member save(String loginId, String password) {
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(password);

        return memberRepository.save(member);
    }
}
