package com.korea.babchingu;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final BoardService boardService;
//    private final TagService tagService;

    public List<Board> getSearchList(String keyword) {
        return boardService.getSearchList(keyword);
    }

}
