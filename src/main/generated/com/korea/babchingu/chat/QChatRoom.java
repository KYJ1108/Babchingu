package com.korea.babchingu.chat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = -2026791643L;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final ListPath<com.korea.babchingu.alarm.Alarm, com.korea.babchingu.alarm.QAlarm> alarmList = this.<com.korea.babchingu.alarm.Alarm, com.korea.babchingu.alarm.QAlarm>createList("alarmList", com.korea.babchingu.alarm.Alarm.class, com.korea.babchingu.alarm.QAlarm.class, PathInits.DIRECT2);

    public final ListPath<ChatMessage, QChatMessage> chatMessageList = this.<ChatMessage, QChatMessage>createList("chatMessageList", ChatMessage.class, QChatMessage.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QChatRoom(String variable) {
        super(ChatRoom.class, forVariable(variable));
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoom(PathMetadata metadata) {
        super(ChatRoom.class, metadata);
    }

}

