package com.korea.babchingu.board;

import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
//    private final TagService tagService;
//    private final BoardTagService boardTagService;
    private final MemberService memberService;

    @GetMapping("/create")
    public String create(BoardForm boardForm) {
        return "board_form";
    }

    @PostMapping("/create")
    public String create(@Valid BoardForm boardForm, BindingResult bindingResult,
                         @RequestParam("images") List<MultipartFile> images,
                         @RequestParam("tags") String tags,
                         Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        if (bindingResult.hasErrors()) {
            return "board_form";
        }

        Board board = boardService.create(boardForm.getTitle(), boardForm.getContent(), images, boardForm.getAddress(), boardForm.getJibun(), boardForm.getRestName(), member);

        // 해시태그 저장
//        String[] tagNames = tags.split(",");
//        for (String tagName : tagNames){
//            Tag tag = tagService.saveTag(tagName.trim());
//            boardTagService.saveBoardTag(board, tag);
//        }
        return "redirect:/board/%d".formatted(board.getId());
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Board board = boardService.getBoard(id);
        if (board == null) {
            // 회사 정보를 찾지 못한 경우 처리 (예: 404 페이지로 리다이렉트)
            return "redirect:/error";
        }
        model.addAttribute("board", board);
        return "board_detail";
    }


    // 더보기
    @GetMapping("/list")
    public String boardList(Model model) {
        // 게시물 목록 가져오기
        List<Board> boards = boardService.getAllBoards();

        // 모델에 게시물 목록 추가
        model.addAttribute("boards", boards);

        return "boardList_form";
    }

    // 장소 검색 - 지도 팝업창
    @GetMapping("/placeSearchPopup")
    public String placeSearchPopup() {
        return "find_rest_form";
    }
}
