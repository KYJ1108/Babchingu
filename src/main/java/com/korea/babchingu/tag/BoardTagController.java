package com.korea.babchingu.tag;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardService;
import com.korea.babchingu.tag.tag.Tag;
import com.korea.babchingu.tag.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boardTag")
@RequiredArgsConstructor
public class BoardTagController {
    private final BoardTagService boardTagService;
    private final BoardService boardService;
    private final TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<?> createBoardTag(@RequestParam Long boardId, @RequestParam String tagName) {
        Board board = boardService.getBoard(boardId); // getBoard 메서드 사용
        Tag tag = tagService.saveTag(tagName);
        BoardTag boardTag = boardTagService.saveBoardTag(board, tag);
        return ResponseEntity.ok(boardTag);
    }
}