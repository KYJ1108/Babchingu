package com.korea.babchingu.follow;

import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;
    private final MemberService memberService;



    // 회원을 팔로우하는 엔드포인트
    @PostMapping("/follow/{loginId}")
    public String followMember(@PathVariable("loginId") String loginId, Principal principal) {
        Member you = memberService.findByLoginId(loginId);
        Member me = memberService.getMember(principal.getName());

        Follow unFollow = followService.unfollow(me,you);
        if(unFollow == null) {
            Follow follow = new Follow();
            follow.setFollower(me);
            follow.setFollowing(you);
            followService.save(follow);
        }else{
            followService.delete(unFollow);
        }

        return "redirect:/profile/%s".formatted(you.getLoginId());
    }
}
