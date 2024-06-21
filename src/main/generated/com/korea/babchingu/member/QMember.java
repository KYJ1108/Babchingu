package com.korea.babchingu.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1556269930L;

    public static final QMember member = new QMember("member1");

    public final ListPath<com.korea.babchingu.board.Board, com.korea.babchingu.board.QBoard> boardList = this.<com.korea.babchingu.board.Board, com.korea.babchingu.board.QBoard>createList("boardList", com.korea.babchingu.board.Board.class, com.korea.babchingu.board.QBoard.class, PathInits.DIRECT2);

    public final ListPath<com.korea.babchingu.chat.ChatRoom, com.korea.babchingu.chat.QChatRoom> chatRoomList = this.<com.korea.babchingu.chat.ChatRoom, com.korea.babchingu.chat.QChatRoom>createList("chatRoomList", com.korea.babchingu.chat.ChatRoom.class, com.korea.babchingu.chat.QChatRoom.class, PathInits.DIRECT2);

    public final ListPath<com.korea.babchingu.comment.Comment, com.korea.babchingu.comment.QComment> commentList = this.<com.korea.babchingu.comment.Comment, com.korea.babchingu.comment.QComment>createList("commentList", com.korea.babchingu.comment.Comment.class, com.korea.babchingu.comment.QComment.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

<<<<<<< HEAD
=======
    public final BooleanPath isCurrentUser = createBoolean("isCurrentUser");

>>>>>>> daa3eabb64183a056a3a5d097ccfec6687e5e3ba
    public final StringPath loginId = createString("loginId");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public final StringPath url = createString("url");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

