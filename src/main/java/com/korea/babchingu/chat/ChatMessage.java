package com.korea.babchingu.chat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.korea.babchingu.image.Image;
import com.korea.babchingu.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    public enum MessageType{
        ENTER,TALK
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 채팅방 번호

    @ManyToOne
    private Member sender; // 메세지 보낸사람

    private String message; // 메시지


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;

    @ManyToOne
    @JsonBackReference
    private ChatRoom chatRoom;

    public ChatMessage(String message){this.message = message;}

    @Builder
    public ChatMessage(Member sender, String message, ChatRoom chatRoom, LocalDateTime createDate){
        this.sender = sender;
        this.message = message;
        this.chatRoom = chatRoom;
        this.createDate = createDate;
    }
}
