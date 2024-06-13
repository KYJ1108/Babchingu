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
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WebSocKetController {
    private final MemberService memberService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final AlarmRepository alarmRepository;

    @MessageMapping("/talk/{id}")
    @SendTo("/sub/talk/{id}")
    public ChatMessageDto message(ChatMessageDto message, @DestinationVariable("id") Long id) throws Exception {
        LocalDateTime createDate = message.getCreateDate();
        Optional<ChatRoom> _chatRoom = chatRoomRepository.findById(id);
        if(!_chatRoom.isEmpty()) {
            ChatRoom chatRoom = _chatRoom.get();
            Member member = memberService.getMember(message.getSender());
            ChatMessage chatMessage = ChatMessage.builder().sender(member).message(message.getMessage()).chatRoom(chatRoom).createDate(createDate).build();
            chatMessageRepository.save(chatMessage);
            return message;
        }
        return null;
    }

    @MessageMapping("/alarm/{username}")
    @SendTo("/sub/alarm/{username}")
    public AlarmDto alarm(AlarmDto alarm) throws Exception {
        Member sendMember = memberRepository.findByNickname(alarm.getSendUsername()).orElseThrow();
        Member acceptMember = memberRepository.findByNickname(alarm.getAcceptUsername()).orElseThrow();
        Optional<ChatRoom> _chatRoom = chatRoomRepository.findById(alarm.getChatRoomId());
        if(!_chatRoom.isEmpty()) {
            ChatRoom chatRoom = _chatRoom.get();
            Alarm alarm1 = Alarm.builder().message(alarm.getMessage()).sendMember(sendMember).acceptMember(acceptMember).chatRoom(chatRoom).build();
            alarmRepository.save(alarm1);
            return alarm;
        }
        return null;
    }

    @GetMapping("/chat/create/{id}")
    public String chatTest(Principal principal, @PathVariable("id")Long id, Model model) {
        Member me = memberService.getMember(principal.getName());
        Member you = memberRepository.findById(id).orElseThrow();

        ChatRoom chatRoom = chatRoomRepository.findByMember1AndMember2OrMember1AndMember2(me, you, you, me).orElse(null);
        if(chatRoom == null) {
            chatRoom = ChatRoom.builder().member1(me).member2(you).build();
            chatRoomRepository.save(chatRoom);
        }

       model.addAttribute("chatroom",chatRoom);
        model.addAttribute("user",me);
       return "chatroom";
    }
}
