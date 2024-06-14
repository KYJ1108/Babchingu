package com.korea.babchingu.member;

import com.korea.babchingu.DataNotFoundException;
import com.korea.babchingu.board.Board;
import com.korea.babchingu.profile.Profile;
import com.korea.babchingu.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.korea.babchingu.profile.QProfile.profile;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

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

        // Member 저장
        member = memberRepository.save(member);

        // Profile 저장
        Profile profile = new Profile();
        profile.setMember(member); // 연관 관계 설정
        profile.setNickname("");
        profile.setImage(null);
        profile.setSex("");
        profile.setPhone("");
        profileRepository.save(profile);

        return member;
    }

    public Member getMember(String loginId) {
        Optional<Member> member = this.memberRepository.findByLoginId(loginId);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public List<Member> getSearchList(String keyword) {
        return memberRepository.findByLoginIdContainingIgnoreCase(keyword);
    }
}
