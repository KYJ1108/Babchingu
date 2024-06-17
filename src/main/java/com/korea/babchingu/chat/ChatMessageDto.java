package com.korea.babchingu.chat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ChatMessageDto {
    private String message;

    private String sender;

    private LocalDateTime createDate;
}
