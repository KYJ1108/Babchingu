package com.korea.babchingu.board;

import com.korea.babchingu.answer.Answer;
import com.korea.babchingu.comment.Comment;
import com.korea.babchingu.image.Image;
import com.korea.babchingu.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    private String address; // 식당 주소
    private String jibun; // 지번 주소
    private String restName;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Image> images;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private Member member;

    @ManyToMany
    Set<Member> voter;
    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updateDate;

    @ElementCollection
    private Set<String> categories = new HashSet<>();

    public int getVoterSize() {
        return this.voter.size();
    }
}
