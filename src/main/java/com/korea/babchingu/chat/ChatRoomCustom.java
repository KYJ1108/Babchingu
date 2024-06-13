package com.korea.babchingu.chat;

import com.korea.babchingu.member.Member;

import java.util.Optional;

public interface ChatRoomCustom {
    Optional<ChatRoom> findByMembers(Member member, Member member2);
}
