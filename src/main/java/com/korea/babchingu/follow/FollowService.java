package com.korea.babchingu.follow;

import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final MemberService memberService;
    private final FollowRepository followRepository;

    public void save(Follow follow) {
        followRepository.save(follow);
    }

    // 자신 프로필 팔로우
    public List<Follow> getMyFollowers(Member member) {
        return Optional.ofNullable(followRepository.findByFollowers(member)).orElse(Collections.emptyList());
    }

    // 팔로잉 목록을 가져오는 메서드
    public List<Follow> getMyFollowing(Member member) {
        return Optional.ofNullable(followRepository.findByFollowing(member)).orElse(Collections.emptyList());
    }

    public Follow unfollow(Member me, Member you) {
        return followRepository.unFollow(me,you);
    }

    public void delete(Follow follow) {
        followRepository.delete(follow);
    }
}
