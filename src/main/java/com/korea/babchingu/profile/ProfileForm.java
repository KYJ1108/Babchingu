package com.korea.babchingu.profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileForm {

    private Long profile_id;

    private Long member_id;
    @NotNull
    private String nickname;

    @NotEmpty
    private byte[] image;
    @NotNull
    private String sex;
    @NotNull
    private String phone;

    private String email;
}

