package com.korea.babchingu.follow;

import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    // 회원을 팔로우하는 메서드
    public void followMember(Long followerId, Long followingId) {
        Member follower = memberRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found"));
        Member following = memberRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("Following not found"));

        // 이미 팔로우 중인지 체크
        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new RuntimeException("이미 팔로우 중입니다.");
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        followRepository.save(follow);
    }

    // 팔로워 수 조회
    public Long countFollowers(Long memberId) {
        return followRepository.countByFollowingId(memberId);
    }

    // 팔로잉 수 조회
    public Long countFollowing(Long memberId) {
        return followRepository.countByFollowerId(memberId);
    }
}
