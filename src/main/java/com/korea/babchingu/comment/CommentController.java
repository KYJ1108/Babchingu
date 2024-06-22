package com.korea.babchingu.comment;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardService;
import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final BoardService boardService;
    private final MemberService memberService;

    @ModelAttribute("memberList")
    public List<Member> member(){
        List<Member> memberList = memberService.findAll();
        return memberList;
    }
    @PostMapping("/create/{id}")
    public String create(Model model, @PathVariable("id") Long id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        Member member = memberService.getMember(principal.getName());
        Board board = boardService.getBoard(id);
        if (bindingResult.hasErrors()) {
            // 필드 검증 오류가 있는 경우 처리
            return "redirect:/board/%d".formatted(board.getId());
        }
        if (commentForm.getContent().trim().isEmpty()) {
            // 댓글 내용이 비어 있는 경우 처리
            return "redirect:/board/%d".formatted(board.getId());
        }
        model.addAttribute("board", board);
        Comment comment = this.commentService.create(board, commentForm.getContent(), member);

        return "redirect:/board/%d".formatted(comment.getBoard().getId());
    }

    @PostMapping("/modify/{id}")
    public String update(@PathVariable("id") Long id, @RequestParam("content") String content, Principal principal) {
        Comment comment = commentService.getComment(id);
        if (!comment.getMember().getLoginId().equals(principal.getName())) {
            return "redirect:/board/%d".formatted(comment.getId());
        }
        commentService.update(id, content);
        return "redirect:/board/%d".formatted(comment.getBoard().getId());
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Principal principal) {
        Comment comment = commentService.getComment(id);
        if (!comment.getMember().getLoginId().equals(principal.getName())) {
            return "redirect:/board/%d".formatted(comment.getId());
        }
        commentService.delete(id);
        return "redirect:/board/%d".formatted(comment.getBoard().getId());
    }
}
