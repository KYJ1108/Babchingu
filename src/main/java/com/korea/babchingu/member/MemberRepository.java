package com.korea.babchingu.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByEmail(String email);

    List<Member> findByNicknameContainingIgnoreCase(String keyword);

    @Query("SELECT DISTINCT m FROM Member m " +
            "JOIN ChatRoom cr ON (m = cr.member1 OR m = cr.member2) " +
            "JOIN ChatMessage cm ON cm.chatRoom = cr " +
            "WHERE cm.sender.id = :memberId AND m.id != :memberId")
    List<Member> findChatMembers(@Param("memberId") Long memberId);
}
