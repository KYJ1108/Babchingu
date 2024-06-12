package com.korea.babchingu.alarm;

import com.korea.babchingu.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AlarmCustomImpl implements AlarmCustom{
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QAlarm qAlarm = QAlarm.alarm;


    @Override
    public List<Alarm> findByAcceptMember(Member acceptMember) {
        return jpaQueryFactory.select(qAlarm).from(qAlarm).where(qAlarm.acceptMember.eq(acceptMember).and(qAlarm.accept.eq(false))).fetch();
    }
}
