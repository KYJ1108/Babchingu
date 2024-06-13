package com.korea.babchingu.chat;

import com.korea.babchingu.alarm.Alarm;
import com.korea.babchingu.alarm.AlarmDto;
import com.korea.babchingu.alarm.AlarmRepository;
import com.korea.babchingu.image.ImageRepository;
import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberRepository;
import com.korea.babchingu.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WebSocKetController {
    private final MemberService memberService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final AlarmRepository alarmRepository;

    @MessageMapping("/talk")
    @SendTo("/sub/talk")
    public ChatMessageDto message(ChatMessageDto message) throws Exception {
//        LocalDateTime createDate = message.getCreateDate();
//        ChatRoom chatRoom = chatRoomRepository.findById(id);
//        Member member = memberService.getUserNickname(message.getSender());
//        ChatMessage chatMessage = ChatMessage.builder().sender(member).message(message.getMessage()).chatRoom(chatRoom).createDate(createDate).build();
//        chatMessageRepository.save(chatMessage);
        return message;
    }

    @MessageMapping("/alarm/{username}")
    @SendTo("/sub/alarm/{username}")
    public AlarmDto alarm(AlarmDto alarm) throws Exception {
        return alarm;
    }

    @GetMapping("/chat/create")
    public String chatTest() {
        return "chatroom";
    }
}
