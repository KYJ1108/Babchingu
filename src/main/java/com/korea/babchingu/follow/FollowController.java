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
    @PostMapping("/follow/{id}")
    public String followMember(@PathVariable("id") Long id, Principal principal) {

        Member you = memberService.findById(id);
        Member me = memberService.getMember(principal.getName());

        Follow follow = new Follow();
        follow.setFollower(me);
        follow.setFollowing(you);
        followService.save(follow);

        return "redirect:/profile/%s".formatted(you.id);
    }

//    @DeleteMapping("/follow/{id}")
//    public ResponseEntity<?> unfollowMember(@PathVariable Long id, Principal principal) {
//        // 언팔로우 처리
//        try {
//            followService.toggleFollow(id, principal.getName());
//            return ResponseEntity.ok().build();
//        } catch (EmptyResultDataAccessException e) {
//            // 팔로우 관계가 없는 경우 처리
//            return ResponseEntity.badRequest().body("팔로우 관계가 존재하지 않습니다.");
//        }
//    }

}
