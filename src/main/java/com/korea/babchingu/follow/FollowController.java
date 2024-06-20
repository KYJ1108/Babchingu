package com.korea.babchingu.follow;

import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;
    private final MemberService memberService;



    // 회원을 팔로우하는 엔드포인트
    @PostMapping("/follow/{longId}")
    public String followMember(@PathVariable("longId") String loginId, Principal principal) {

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

//    // 회원을 언팔로우하는 엔드포인트
//    @PostMapping("/unfollow/{id}")
//    public String unfollowMember(@PathVariable("id") Long id, Principal principal) {
//        Member you = memberService.findById(id);
//        Member me = memberService.getMember(principal.getName());
//
//        // 언팔로우 로직 수행
//       Follow follow = followService.unfollow(me, you);
//       followService.delete(follow);
//
//        return "redirect:/profile/%s".formatted(you.id);
//    }
}
