package com.korea.babchingu.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContainingIgnoreCaseOrRestNameContainingIgnoreCase(String keyword1, String keyword2);
}
