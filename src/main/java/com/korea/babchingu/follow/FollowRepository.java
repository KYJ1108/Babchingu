package com.korea.babchingu.follow;

import com.korea.babchingu.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FollowRepository extends JpaRepository<Follow, Long> , FollowCustom {
    List<Follow> findByFollowers(Member member);
    List<Follow> findByFollowing(Member member);

    Long countByFollowerId(Long followerId); // 팔로워 수 조회

    boolean existsByFollowerAndFollowing(Member follower, Member following);
}
