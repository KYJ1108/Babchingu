package com.korea.babchingu.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board create(String title, String content) {
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);

        boardRepository.save(board);

        return board;
    }
}
