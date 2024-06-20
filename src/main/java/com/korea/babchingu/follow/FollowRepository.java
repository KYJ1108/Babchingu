package com.korea.babchingu.follow;

import com.korea.babchingu.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FollowRepository extends JpaRepository<Follow, Long> , FollowCustom {
    List<Follow> findByFollowers(Member member);
    List<Follow> findByFollowing(Member member);

    // 언팔로우
//    @Query("SELECT f FROM Follow f WHERE f.follower = :follower AND f.following = :following")
//    Follow findByFollowerAndFollowing(@Param("follower") Member me, @Param("following") Member you);
}
