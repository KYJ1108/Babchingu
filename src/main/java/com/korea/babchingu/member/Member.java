package com.korea.babchingu.member;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.chat.ChatRoom;
import com.korea.babchingu.comment.Comment;
import com.korea.babchingu.follow.Follow;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true)
    private String loginId;
    private String password;
    private String email;
    private String nickname;

    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Board> boardList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @ManyToMany
    private List<ChatRoom> chatRoomList = new ArrayList<>();

    private String url; // 프로필 이미지 URL

}
