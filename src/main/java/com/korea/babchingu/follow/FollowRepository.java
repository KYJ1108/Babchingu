package com.korea.babchingu.follow;

import com.korea.babchingu.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FollowRepository extends JpaRepository<Follow, Long> , FollowCustom {

    Long countByFollowerId(Long followerId); // 팔로워 수 조회

}
