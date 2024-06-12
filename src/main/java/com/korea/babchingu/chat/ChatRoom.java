package com.korea.babchingu.chat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.korea.babchingu.alarm.Alarm;
import com.korea.babchingu.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    List<ChatMessage> chatMessageList = new ArrayList<>();

//    @ManyToOne
//    private Member member;
//
//    @ManyToOne
//    private Member member2;

    @OneToMany(mappedBy = "chatRoom",cascade = CascadeType.REMOVE)
    @JsonManagedReference
    List<Alarm> alarmList = new ArrayList<>();


//    private ChatRoom(Member member1, Member member2) {
//        this.member = member1;
//        this.member2 = member2;
//    }
}
