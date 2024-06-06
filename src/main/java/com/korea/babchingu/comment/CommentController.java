package com.korea.babchingu.comment;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final BoardService boardService;
    @PostMapping("/create/{id}")
    public String create(Model model, @PathVariable("id") Long id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        Board board = boardService.getBoard(id);
        if (bindingResult.hasErrors()) {
            return "question_detail";
        }
        model.addAttribute("board", board);
        Comment comment = this.commentService.create(board, commentForm.getContent());

        return "redirect:/board/%d".formatted(comment.getBoard().getId());
    }
}
