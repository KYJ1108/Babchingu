package com.korea.babchingu.member;

import com.korea.babchingu.DataNotFoundException;
import com.korea.babchingu.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Member getMember(String loginId) {
        Optional<Member> member = this.memberRepository.findByLoginId(loginId);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }

    public String findId(String name, String email) {
        // 이름과 이메일을 사용하여 사용자를 찾습니다.
        Member member = memberRepository.findByNicknameAndEmail(name, email);

        // 사용자가 존재하는 경우 아이디를 반환합니다.
        if (member != null) {
            return member.getLoginId();
        } else {
            // 사용자가 존재하지 않는 경우 null이 아니라 어떤 값으로 처리해야 할지에 대한 로직을 추가할 수 있습니다.
            return null;
        }
    }
}
