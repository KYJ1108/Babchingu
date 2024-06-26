package com.korea.babchingu.answer;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardService;
import com.korea.babchingu.comment.Comment;
import com.korea.babchingu.comment.CommentService;
import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final MemberService memberService;
    private final CommentService commentService;
    private final BoardService boardService;
    @PostMapping("/create/{id}")
    public String create(Model model, @PathVariable("id") Long id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Member member = memberService.getMember(principal.getName());
        Comment comment = commentService.getComment(id);
        Board board = boardService.getBoard(id);

        if (bindingResult.hasErrors()) {
            return "redirect:/board/%d".formatted(board.getId());
        }
        if (answerForm.getContent().trim().isEmpty()) {
            // 대댓글 내용이 비어 있는 경우 처리
            return "redirect:/board/%d".formatted(board.getId());
        }
        model.addAttribute("comment", comment);
        Answer answer = this.answerService.create(comment, answerForm.getContent(), member);

        return "redirect:/board/%d".formatted(answer.getComment().getBoard().getId());
    }

    @PostMapping("/modify/{id}")
    public String update(@PathVariable("id") Long id, @RequestParam("content") String content, Principal principal) {
        Answer answer = answerService.update(id, content);
        if (!answer.getMember().getLoginId().equals(principal.getName())) {
            return "redirect:/board/%d".formatted(answer.getComment().getBoard().getId());
        }
        return "redirect:/board/%d".formatted(answer.getComment().getBoard().getId());
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        Answer answer = answerService.getAnswer(id);
        answerService.delete(id);
        return "redirect:/board/%d".formatted(answer.getComment().getBoard().getId());
    }
}
