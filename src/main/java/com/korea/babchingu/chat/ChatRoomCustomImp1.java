package com.korea.babchingu.chat;

import com.korea.babchingu.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;

public class ChatRoomCustomImp1 implements ChatRoomCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QChatRoom qChatRoom = QChatRoom.chatRoom;

//    @Override
//    public Optional<ChatRoom> findByMembers(Member member, Member member2) {
//        return Optional.ofNullable(jpaQueryFactory
//                .select(qChatRoom)
//                .from(qChatRoom)
//                .where((qChatRoom.member.eq(member).and(qChatRoom.member2.eq(member2)))
//                        .or((qChatRoom.member.eq(member2).and(qChatRoom.member2.eq(member)))))
//                .fetchOne());
//
//
//    }
}

