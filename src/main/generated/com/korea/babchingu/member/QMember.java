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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final ListPath<com.korea.babchingu.board.Board, com.korea.babchingu.board.QBoard> boardList = this.<com.korea.babchingu.board.Board, com.korea.babchingu.board.QBoard>createList("boardList", com.korea.babchingu.board.Board.class, com.korea.babchingu.board.QBoard.class, PathInits.DIRECT2);

    public final ListPath<com.korea.babchingu.chat.ChatRoom, com.korea.babchingu.chat.QChatRoom> chatRoomList = this.<com.korea.babchingu.chat.ChatRoom, com.korea.babchingu.chat.QChatRoom>createList("chatRoomList", com.korea.babchingu.chat.ChatRoom.class, com.korea.babchingu.chat.QChatRoom.class, PathInits.DIRECT2);

    public final ListPath<com.korea.babchingu.comment.Comment, com.korea.babchingu.comment.QComment> commentList = this.<com.korea.babchingu.comment.Comment, com.korea.babchingu.comment.QComment>createList("commentList", com.korea.babchingu.comment.Comment.class, com.korea.babchingu.comment.QComment.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final ListPath<com.korea.babchingu.Follow.Follow, com.korea.babchingu.Follow.QFollow> followerList = this.<com.korea.babchingu.Follow.Follow, com.korea.babchingu.Follow.QFollow>createList("followerList", com.korea.babchingu.Follow.Follow.class, com.korea.babchingu.Follow.QFollow.class, PathInits.DIRECT2);

    public final ListPath<com.korea.babchingu.Follow.Follow, com.korea.babchingu.Follow.QFollow> followingList = this.<com.korea.babchingu.Follow.Follow, com.korea.babchingu.Follow.QFollow>createList("followingList", com.korea.babchingu.Follow.Follow.class, com.korea.babchingu.Follow.QFollow.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath loginId = createString("loginId");

    public final StringPath password = createString("password");

    public final com.korea.babchingu.profile.QProfile profile;

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.profile = inits.isInitialized("profile") ? new com.korea.babchingu.profile.QProfile(forProperty("profile"), inits.get("profile")) : null;
    }

}

