package com.korea.babchingu.board.tag;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.tag.tag.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoardTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Board board;

    @ManyToOne
    private Tag tag;
}
