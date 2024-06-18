package com.korea.babchingu.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContainingIgnoreCaseOrRestNameContainingIgnoreCase(String keyword1, String keyword2);

    // 좋아요 게시물 3개
    @Query("SELECT b FROM Board b LEFT JOIN b.voter v GROUP BY b.id ORDER BY COUNT(v) DESC")
    List<Board> findTop3ByOrderByVoterDesc();

    List<Board> findByCategoriesIn(List<String> categories);
}
