//package com.korea.babchingu.category;
//
//import com.korea.babchingu.board.Board;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//public class Category {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
//    private List<Board> boardList;
//
//    @Column(columnDefinition = "TEXT")
//    private String name; // 카테고리 명
//
//    public Category(String name){
//        this.name = name;
//    }
//
//    public Category(){
//
//    }
//}