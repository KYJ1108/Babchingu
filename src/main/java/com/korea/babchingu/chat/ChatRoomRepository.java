package com.korea.babchingu.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String>, ChatRoomCustom {
    public ChatRoom findById(Long id);

    public List<ChatRoom> findAllById(Long id);
}
