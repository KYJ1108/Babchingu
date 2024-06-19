package com.korea.babchingu.follow;

import com.korea.babchingu.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowCustomImpl implements FollowCustom{
    private final JPAQueryFactory jpaQueryFactory;

    QFollow qFollow =QFollow.follow;


    @Override
    public List<Follow> findByFollowers(Member member) {
        return jpaQueryFactory.select(qFollow).from(qFollow).where(qFollow.following.eq(member)).fetch();
    }

    @Override
    public List<Follow> findByFollowing(Member member){
        return jpaQueryFactory.select(qFollow).from(qFollow).where(qFollow.follower.eq(member)).fetch();
    }
}
