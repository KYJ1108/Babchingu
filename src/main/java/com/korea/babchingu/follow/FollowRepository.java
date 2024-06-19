package com.korea.babchingu.follow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Long countByFollowingId(Long followingId); // 팔로우하는 회원 수 조회

    Long countByFollowerId(Long followerId); // 팔로워 수 조회
}
