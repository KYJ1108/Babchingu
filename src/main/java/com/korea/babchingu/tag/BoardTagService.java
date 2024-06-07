package com.korea.babchingu.tag;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.tag.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardTagService {
    private final BoardTagRepository boardTagRepository;

    public BoardTag saveBoardTag(Board board, Tag tag) {
        BoardTag boardTag = new BoardTag();
        boardTag.setBoard(board);
        boardTag.setTag(tag);
        return boardTagRepository.save(boardTag);
    }
}