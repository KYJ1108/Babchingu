package com.korea.babchingu.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);
    Member findByNicknameAndEmail(String name, String email);

    @Query("SELECT m FROM Member m JOIN FETCH m.profile WHERE m.loginId = :loginId")
    Optional<Member> findByLoginIdWithProfile(@Param("loginId") String loginId);
}
