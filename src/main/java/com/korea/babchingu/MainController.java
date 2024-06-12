package com.korea.babchingu;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardService;
import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final MemberService memberService;

    @GetMapping("/")
    public String main(Model model, Principal principal) {
        if (principal != null) {
            Member member = memberService.getMember(principal.getName());
            model.addAttribute("member", member);
        }
        return "main";
    }

    @GetMapping("/map")
    public String map() {
        return "map";
    }

    @GetMapping("/search")
    public String search(Model model,@RequestParam String searchInput) {

        List<Board> searchBoard = mainService.getSearchList(searchInput);
        model.addAttribute("searchBoard", searchBoard);

        return "search";
    }
}