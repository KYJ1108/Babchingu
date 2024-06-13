package com.korea.babchingu.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String loginId;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotEmpty(message = "비밀번호를 동일하게 입력해주세요.")
    private String password2;
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String nickname;
    @NotEmpty(message = "이미지를 업로드해주세요.")
    private byte[] image;
    @NotEmpty(message = "성별을 입력해주세요.")
    private String sex;
    @NotEmpty(message = "핸드폰번호를 입력해주세요.")
    private String phone;
}
