package com.korea.babchingu.Follow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDTO {
    private Long memberId;  // 팔로우 대상 회원의 ID
    private String nickname;  // 팔로우 대상 회원의 닉네임
    private boolean isFollowing;  // 현재 사용자가 해당 회원을 팔로우 중인지 여부

    // Constructors
    public FollowDTO(Long memberId, String nickname, boolean isFollowing) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.isFollowing = isFollowing;
    }
}
