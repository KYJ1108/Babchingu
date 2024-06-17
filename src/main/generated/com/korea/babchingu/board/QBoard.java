package com.korea.babchingu.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 1796151194L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final StringPath address = createString("address");

    public final ListPath<com.korea.babchingu.answer.Answer, com.korea.babchingu.answer.QAnswer> answerList = this.<com.korea.babchingu.answer.Answer, com.korea.babchingu.answer.QAnswer>createList("answerList", com.korea.babchingu.answer.Answer.class, com.korea.babchingu.answer.QAnswer.class, PathInits.DIRECT2);

    public final SetPath<String, StringPath> categories = this.<String, StringPath>createSet("categories", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<com.korea.babchingu.comment.Comment, com.korea.babchingu.comment.QComment> commentList = this.<com.korea.babchingu.comment.Comment, com.korea.babchingu.comment.QComment>createList("commentList", com.korea.babchingu.comment.Comment.class, com.korea.babchingu.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.korea.babchingu.image.Image, com.korea.babchingu.image.QImage> images = this.<com.korea.babchingu.image.Image, com.korea.babchingu.image.QImage>createList("images", com.korea.babchingu.image.Image.class, com.korea.babchingu.image.QImage.class, PathInits.DIRECT2);

    public final StringPath jibun = createString("jibun");

    public final com.korea.babchingu.member.QMember member;

    public final StringPath restName = createString("restName");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public final SetPath<com.korea.babchingu.member.Member, com.korea.babchingu.member.QMember> voter = this.<com.korea.babchingu.member.Member, com.korea.babchingu.member.QMember>createSet("voter", com.korea.babchingu.member.Member.class, com.korea.babchingu.member.QMember.class, PathInits.DIRECT2);

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.korea.babchingu.member.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

