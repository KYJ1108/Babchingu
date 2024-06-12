package com.korea.babchingu.profile;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Profile {

    @NotEmpty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

