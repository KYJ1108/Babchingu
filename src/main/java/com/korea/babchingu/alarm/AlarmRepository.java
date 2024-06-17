package com.korea.babchingu.alarm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmCustom {
    List<Alarm> findByChatRoomId(Long id);
}
