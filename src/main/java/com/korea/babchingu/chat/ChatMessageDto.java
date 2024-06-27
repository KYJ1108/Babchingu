package com.korea.babchingu.chat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ChatMessageDto {
    private String message;

    private String sender;

    private String senderImgUrl;

    private LocalDateTime sendTime;


    private String loginId;
}
