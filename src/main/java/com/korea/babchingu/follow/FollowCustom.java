package com.korea.babchingu.follow;

import com.korea.babchingu.member.Member;

import java.util.List;

public interface FollowCustom {
    List<Follow> findByFollowers(Member member);
    List<Follow> findByFollowing(Member member);
}
