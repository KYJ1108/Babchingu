package com.korea.babchingu.alarm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmDto {
    private String message;

    private Boolean accept;

    private String sendUsername;

    private String acceptUsername;

    private Long chatRoomId;
}
