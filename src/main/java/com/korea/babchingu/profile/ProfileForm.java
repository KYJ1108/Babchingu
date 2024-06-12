package com.korea.babchingu.profile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileForm {

    private Long profile_id;

    private Long member_id;

    private String nickname;

    @NotEmpty
    private byte[] image;

    private String sex;

    private String phone;

    private String email;
}

