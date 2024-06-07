package com.korea.babchingu.comment;

import com.korea.babchingu.DataNotFoundException;
import com.korea.babchingu.board.Board;
import com.korea.babchingu.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public Comment create(Board board, String content, Member member) {
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setContent(content);
        comment.setMember(member);

        commentRepository.save(comment);

        return comment;
    }

    public Comment getComment(Long id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public Comment update(Long id, String content) {
        Comment targetComment = getComment(id);
        targetComment.setContent(content);
        return commentRepository.save(targetComment);
    }


    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
