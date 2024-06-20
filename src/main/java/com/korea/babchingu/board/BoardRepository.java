package com.korea.babchingu.board;

import com.korea.babchingu.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContainingIgnoreCaseOrRestNameContainingIgnoreCase(String keyword1, String keyword2);

    // 좋아요 게시물 3개
    @Query("SELECT b FROM Board b LEFT JOIN b.voter v GROUP BY b.id ORDER BY COUNT(v) DESC")
    List<Board> findTop3ByOrderByVoterDesc();

    List<Board> findByCategoriesIn(List<String> categories);

    // 최신순 정렬
    @Query("SELECT b FROM Board b ORDER BY b.createDate DESC")
    Page<Board> findAllByOrderByCreateDateDesc(Pageable pageable);

    // 인기순 정렬
    @Query("SELECT b FROM Board b ORDER BY SIZE(b.voter) DESC")
    Page<Board> findAllByOrderByVoterSizeDesc(Pageable pageable);

    List<Board> findByMember(Member member);
}
