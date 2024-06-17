package com.korea.babchingu.chat;

import com.korea.babchingu.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomCustom {
    Optional<ChatRoom> findByMember1AndMember2OrMember1AndMember2(Member me, Member you, Member you2, Member me2);
}
