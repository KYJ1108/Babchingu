package com.korea.babchingu.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    // 회원을 팔로우하는 엔드포인트
    @PostMapping("/follow")
    public ResponseEntity<String> followMember(@RequestParam Long followerId, @RequestParam Long followingId) {
        try {
            followService.followMember(followerId, followingId);
            return ResponseEntity.ok("Successfully followed member");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 특정 회원의 팔로워 수 조회하는 엔드포인트
    @GetMapping("/followers/{memberId}")
    public ResponseEntity<Long> countFollowers(@PathVariable Long memberId) {
        Long followerCount = followService.countFollowers(memberId);
        return ResponseEntity.ok(followerCount);
    }
}
