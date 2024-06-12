package com.korea.babchingu.profile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileForm {

    @NotEmpty
    private Long profile_id;

    @NotEmpty
    private Long member_id;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private byte[] image;

    @NotEmpty
    private String sex;
}

