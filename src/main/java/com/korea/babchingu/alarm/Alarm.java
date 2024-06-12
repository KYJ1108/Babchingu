package com.korea.babchingu.alarm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.korea.babchingu.chat.ChatRoom;
import com.korea.babchingu.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private Boolean accept;

    @ManyToOne
    private Member sendMember;

    @ManyToOne
    private Member acceptMember;

    @ManyToOne
    @JsonBackReference
    private ChatRoom chatRoom;


    @Builder
    public Alarm(String message, Member sendMember, Member acceptMember, ChatRoom chatRoom) {
        this.message = message;
        this.sendMember = sendMember;
        this.acceptMember = acceptMember;
        this.chatRoom = chatRoom;
        this.accept = false;

    }
}
