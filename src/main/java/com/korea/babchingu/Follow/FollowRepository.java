package com.korea.babchingu.Follow;

import com.korea.babchingu.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFromMember(Member fromMember);
    List<Follow> findByToMember(Member toMember);
    boolean existsByFromMemberAndToMember(Member fromMember, Member toMember);
}