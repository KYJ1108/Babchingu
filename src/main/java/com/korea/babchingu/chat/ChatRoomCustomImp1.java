package com.korea.babchingu.chat;


import com.korea.babchingu.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ChatRoomCustomImp1 implements ChatRoomCustom {


    private final JPAQueryFactory jpaQueryFactory;

    QChatRoom qChatRoom = QChatRoom.chatRoom;


    @Override
    public Optional<ChatRoom> findByMembers(Member member, Member member2) {
        return Optional.ofNullable(jpaQueryFactory
                .select(qChatRoom)
                .from(qChatRoom)
                .where((qChatRoom.member.eq(member).and(qChatRoom.member2.eq(member2)))
                        .or((qChatRoom.member.eq(member2).and(qChatRoom.member2.eq(member)))))
                .fetchOne());
    }
}

