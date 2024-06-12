package com.korea.babchingu.board.tag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardTag is a Querydsl query type for BoardTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardTag extends EntityPathBase<BoardTag> {

    private static final long serialVersionUID = 305183604L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardTag boardTag = new QBoardTag("boardTag");

    public final com.korea.babchingu.board.QBoard board;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.korea.babchingu.board.tag.tag.QTag tag;

    public QBoardTag(String variable) {
        this(BoardTag.class, forVariable(variable), INITS);
    }

    public QBoardTag(Path<? extends BoardTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardTag(PathMetadata metadata, PathInits inits) {
        this(BoardTag.class, metadata, inits);
    }

    public QBoardTag(Class<? extends BoardTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.korea.babchingu.board.QBoard(forProperty("board"), inits.get("board")) : null;
        this.tag = inits.isInitialized("tag") ? new com.korea.babchingu.board.tag.tag.QTag(forProperty("tag")) : null;
    }

}

