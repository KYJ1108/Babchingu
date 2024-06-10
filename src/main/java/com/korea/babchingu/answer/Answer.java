package com.korea.babchingu.answer;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.comment.Comment;
import com.korea.babchingu.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private Board board;

    @ManyToOne
    private Member member;
}
