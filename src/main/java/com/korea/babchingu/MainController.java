package com.korea.babchingu;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardService;
import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final MemberService memberService;
    private final BoardService boardService;

    @ModelAttribute("memberList")
    public List<Member> member(Principal principal) {
        if (principal != null) {
            try {
                return memberService.findChatMembersByPrincipal(principal);
            } catch (NoSuchElementException e) {
                // 로그를 남기거나, 예외 처리를 추가할 수 있습니다.
                // 예외 발생 시 빈 리스트 반환
                return Collections.emptyList();
            }
        }
        return Collections.emptyList(); // 빈 리스트를 반환하여 예외를 방지합니다.
    }

    @ModelAttribute("memberUrl")
    public String memberUrl(Principal principal) {
        if (principal == null) {
            return "/path/to/default/memberUrl"; // 로그인하지 않은 경우 기본 URL 반환
        }

        Member member = memberService.getMember(principal.getName());
        if (member != null && member.getLoginId().equals(principal.getName())) {
            String memberUrl = member.getUrl();
            return memberUrl;
        }else {
            return "/path/to/default/memberUrl";
        }
    }

    @GetMapping("/")
    public String main(Model model, Principal principal) {
        if (principal != null) {
            Member member = memberService.getMember(principal.getName());
            model.addAttribute("member", member);
        }
        Member member = memberService.getMember(principal.getName());
        if (member.getNickname() == null) {
            return "redirect:/modifyProfile";
        }
        // 인기 있는 게시물 가져오기 (좋아요 순으로 상위 3개)
        List<Board> popularBoards = mainService.getPopularBoards();
        model.addAttribute("popularBoards", popularBoards);

        return "main";
    }

    @GetMapping("/map")
    public String map() {
        return "map";
    }

    @PostMapping("/search")
    public String search(Model model,@RequestParam String searchInput) {

        List<Board> searchBoard = mainService.getBoardSearchList(searchInput);
        List<Member> searchMember = mainService.getMemberSearchList(searchInput);
        model.addAttribute("searchBoard", searchBoard);
        model.addAttribute("searchMember", searchMember);

        return "search";
    }
}