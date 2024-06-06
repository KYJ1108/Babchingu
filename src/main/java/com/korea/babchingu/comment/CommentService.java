package com.korea.babchingu.comment;

import com.korea.babchingu.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public Comment create(Board board, String content) {
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setContent(content);

        commentRepository.save(comment);

        return comment;
    }
}
