package com.korea.babchingu.profile;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProfile is a Querydsl query type for Profile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProfile extends EntityPathBase<Profile> {

    private static final long serialVersionUID = 1267086880L;

    public static final QProfile profile = new QProfile("profile");

    public final StringPath email = createString("email");

    public final ArrayPath<byte[], Byte> image = createArray("image", byte[].class);

    public final NumberPath<Long> member_id = createNumber("member_id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath phone = createString("phone");

    public final NumberPath<Long> profile_id = createNumber("profile_id", Long.class);

    public final StringPath sex = createString("sex");

    public QProfile(String variable) {
        super(Profile.class, forVariable(variable));
    }

    public QProfile(Path<? extends Profile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProfile(PathMetadata metadata) {
        super(Profile.class, metadata);
    }

}

