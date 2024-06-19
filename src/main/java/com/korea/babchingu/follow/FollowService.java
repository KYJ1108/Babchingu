package com.korea.babchingu.follow;

import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberRepository;
import com.korea.babchingu.member.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final MemberService memberService;
    private final FollowRepository followRepository;

    public void save(Follow follow) {
        followRepository.save(follow);
    }

//    public void toggleFollow(Long memberIdToFollow, String followerUsername) {
//        // 팔로우 상태 조회
//        Optional<Follow> existingFollow = followRepository.findByFollowerIdAndFollowingId(followerId, memberIdToFollow);
//
//        if (existingFollow.isPresent()) {
//            // 이미 팔로우한 상태면 언팔로우 처리
//            followRepository.delete(existingFollow.get());
//        } else {
//            // 팔로우하지 않은 상태면 팔로우 처리
//            Member follower = memberService.findByUsername(followerUsername);
//            Member following = memberService.findById(memberIdToFollow);
//
//            Follow follow = new Follow();
//            follow.setFollower(follower);
//            follow.setFollowing(following);
//            followRepository.save(follow);
//        }
//    }
}
