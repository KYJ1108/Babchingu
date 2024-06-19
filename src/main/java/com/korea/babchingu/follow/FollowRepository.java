package com.korea.babchingu.follow;

import com.korea.babchingu.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Long countByFollowingId(Long followingId); // 팔로우하는 회원 수 조회

    Long countByFollowerId(Long followerId); // 팔로워 수 조회

    // 팔로우 관계가 이미 존재하는지를 확인하는 사용자 정의 쿼리 메서드
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Follow f " +
            "WHERE f.follower = :follower AND f.following = :following")
    boolean existsByFollowerAndFollowing(@Param("follower") Member follower, @Param("following") Member following);
}
