package com.korea.babchingu.board;

import com.korea.babchingu.comment.Comment;
import com.korea.babchingu.image.Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Image> images;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;
}
