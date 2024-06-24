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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class WebSocKetController {
    private final MemberService memberService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final AlarmRepository alarmRepository;

    @ModelAttribute("memberList")
    public List<Member> member(Principal principal) {
        if (principal != null) {
            return memberService.findChatMembersByPrincipal(principal);
        }
        return null; // 또는 빈 리스트를 반환하거나 기본값을 설정할 수 있습니다.
    }

    @MessageMapping("/talk/{id}")
    @SendTo("/sub/talk/{id}")
    public ChatMessageDto message(ChatMessageDto message, @DestinationVariable("id") Long id) throws Exception {

        Optional<ChatRoom> _chatRoom = chatRoomRepository.findById(id);
        if(!_chatRoom.isEmpty()) {
            ChatRoom chatRoom = _chatRoom.get();
            Member member = memberService.getMember(message.getSender());
            ChatMessage chatMessage = ChatMessage.builder().sender(member).message(message.getMessage()).chatRoom(chatRoom).createDate(LocalDateTime.now()).build();
            chatMessageRepository.save(chatMessage);
            LocalDateTime createDate = LocalDateTime.now();
            message.setSenderImgUrl(member.getUrl());
            message.setSender(member.getNickname());
            message.setCreateDate(createDate);
            message.setLoginId(member.getLoginId());

            return message;
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
            model.addAttribute("chatroom",chatRoom);
        }else{
            model.addAttribute("chatroom",chatRoom);
        }


        model.addAttribute("user",me);
        model.addAttribute("opponentName", you.getNickname());

       return "chatroom";
    }
}
