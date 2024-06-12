package com.korea.babchingu.alarm;

import com.korea.babchingu.member.Member;

import java.util.List;

public interface AlarmCustom {
    List<Alarm> findByAcceptMember(Member acceptMember);
}
