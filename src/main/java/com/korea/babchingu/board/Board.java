package com.korea.babchingu.board;

import com.korea.babchingu.answer.Answer;
import com.korea.babchingu.comment.Comment;
import com.korea.babchingu.image.Image;
import com.korea.babchingu.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    private String address; // 식당 주소
    private String jibun; // 지번 주소
    private String restName;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Image> images;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private Member member;

    @ManyToMany
    Set<Member> voter;
}
