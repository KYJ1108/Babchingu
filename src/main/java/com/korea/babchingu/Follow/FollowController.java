package com.korea.babchingu.Follow;

import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberRepository;
import com.korea.babchingu.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FollowController {

    private final FollowService followService;
    private final MemberRepository memberRepository;

    @Autowired
    public FollowController(FollowService followService, MemberRepository memberRepository) {
        this.followService = followService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/following")
    public ResponseEntity<List<FollowDTO>> getFollowingList(@RequestParam Long selectedMemberId, @RequestParam Long requestMemberId) {
        Member selectedMember = memberRepository.findById(selectedMemberId).orElseThrow();
        Member requestMember = memberRepository.findById(requestMemberId).orElseThrow();
        List<FollowDTO> followingList = followService.followingList(selectedMember, requestMember);
        return ResponseEntity.ok(followingList);
    }

    @GetMapping("/followers")
    public ResponseEntity<List<FollowDTO>> getFollowerList(@RequestParam Long selectedMemberId, @RequestParam Long requestMemberId) {
        Member selectedMember = memberRepository.findById(selectedMemberId).orElseThrow();
        Member requestMember = memberRepository.findById(requestMemberId).orElseThrow();
        List<FollowDTO> followerList = followService.followerList(selectedMember, requestMember);
        return ResponseEntity.ok(followerList);
    }


}
