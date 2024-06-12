package com.korea.babchingu.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberPwRequestDto {

    private String loginId;

    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;
}
