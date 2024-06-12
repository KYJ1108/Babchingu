package com.korea.babchingu.chat;

import com.korea.babchingu.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
//    public Optional<ChatRoom> getChatRoom(Member member1 , Member member2){
//        return this.chatRoomRepository.findByMembers(member1,member2);
//
//    }

    public ChatRoom save(ChatRoom chatRoom){
        return this.chatRoomRepository.save(chatRoom);
    }

    public ChatRoom findById(Long id){
        return chatRoomRepository.findById(id);
    }
}