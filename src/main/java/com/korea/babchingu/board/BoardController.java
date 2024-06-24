package com.korea.babchingu.board;

import com.korea.babchingu.image.Image;
import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
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

    @ModelAttribute("memberList")
    public List<Member> member(){
        List<Member> memberList = memberService.findAll();
        return memberList;
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

    @GetMapping("/create")
    public String create(BoardForm boardForm) {
        return "board_form";
    }

    @PostMapping("/create")
    public String create(@Valid BoardForm boardForm, BindingResult bindingResult,
                         @RequestParam("images") List<MultipartFile> images,
                         Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        if (bindingResult.hasErrors()) {
            return "board_form";
        }

        Board board = boardService.create(boardForm.getTitle(), boardForm.getContent(), images, boardForm.getAddress(), boardForm.getJibun(), boardForm.getRestName(), boardForm.getCategories(), member, boardForm.getCreateDate());
        // 해시태그 저장
//        String[] tagNames = tags.split(",");
//        for (String tagName : tagNames){
//            Tag tag = tagService.saveTag(tagName.trim());
//            boardTagService.saveBoardTag(board, tag);
//        }
        return "redirect:/board/%d".formatted(board.getId());
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model, Principal principal) {
        Board board = boardService.getBoard(id);

        // 현재 로그인한 사용자와 프로필 주인의 아이디를 비교하여 모델에 추가
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInMemberId = authentication.getName();
        model.addAttribute("loggedInMemberId", loggedInMemberId);

        String loggedInUsername = principal.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        if (board == null) {
            return "redirect:/error";
        }
        model.addAttribute("board", board);
        if (principal != null) {
            Member member = memberService.getMember(principal.getName());
            model.addAttribute("member", member);
        }
        return "board_detail";
    }

    // 더보기
    @GetMapping("/list")
    public String boardList(Model model, Principal principal) {
        // 게시물 목록 가져오기
        List<Board> boards = boardService.getAllBoards();

        // 모델에 게시물 목록 추가
        model.addAttribute("boards", boards);

        if (principal != null) {
            Member member = memberService.getMember(principal.getName());
            model.addAttribute("member", member);
        }
        return "redirect:/board/date";
    }

    // 장소 검색 - 지도 팝업창
    @GetMapping("/placeSearchPopup")
    public String placeSearchPopup() {
        return "find_rest_form";
    }

    @GetMapping("/modify/{id}")
    public String update(BoardForm boardForm, @PathVariable("id") Long id, Model model, Principal principal) {
        // id를 사용하여 해당 게시글을 데이터베이스에서 가져오는 작업 수행
        Board board = boardService.getBoard(id);

        List<Image> images = board.getImages();

        if (!board.getMember().getLoginId().equals(principal.getName())) {
            return "redirect:/board/%d".formatted(board.getId());
        }
        // 가져온 게시글이 null이 아닌 경우에만 모델에 값을 설정
        if (board != null) {
            // 해당 게시글의 내용을 boardForm에 설정
            boardForm.setTitle(board.getTitle());
            boardForm.setContent(board.getContent());
            boardForm.setRestName(board.getRestName());
            boardForm.setAddress(board.getAddress());
            boardForm.setJibun(board.getJibun());

            model.addAttribute("boardForm", boardForm); // 폼에 사용할 boardForm을 모델에 추가
            model.addAttribute("images", images);
        } else {
            // 게시글이 존재하지 않는 경우에 대한 예외 처리
            return "redirect:/error";
        }
        return "board_modify";
    }

    @PostMapping("/modify/{id}")
    public String update(@PathVariable("id") Long id,
                         @RequestParam("content") String content,
                         @Valid BoardForm boardForm,
                         @RequestParam("images") List<MultipartFile> images,
                         Model model, Principal principal) {
        Board board = boardService.getBoard(id);
        if (!board.getMember().getLoginId().equals(principal.getName())) {
            return "redirect:/board/%d".formatted(board.getId());
        }
        boardService.update(id, boardForm.getTitle(), boardForm.getContent(), images, boardForm.getAddress(), boardForm.getJibun(), boardForm.getRestName(), boardForm.getCategories(), boardForm.getUpdateDate());
        model.addAttribute("board", board);
        return "redirect:/board/%d".formatted(board.getId());
    }

    @PostMapping("/delete/image/{deleteImageId}")
    public String imageDelete(@PathVariable("deleteImageId") Long deleteImageId, @RequestParam("id") Long id) {
        // 게시물 ID로 게시물을 가져옵니다.
        Board board = boardService.getBoard(id);

        // 이미지 삭제 서비스 호출
        boardService.imageDelete(deleteImageId);

        // 게시물 수정 페이지로 리디렉션
        return "redirect:/board/modify/" + board.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Principal principal) {
        Board board = boardService.getBoard(id);
        if (!board.getMember().getLoginId().equals(principal.getName())) {
            return "redirect:/board/%d".formatted(board.getId());
        }
        boardService.delete(id);

        return "redirect:/profile/%s".formatted(board.getMember().getLoginId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/{id}/vote")
    public String boardVote(Principal principal, @PathVariable("id") Long id) {
        Board board = this.boardService.getBoard(id);
        Member member = this.memberService.getMember(principal.getName());
        if (board.getVoter().contains(member)) {
            this.boardService.cancelVote(board, member);
        } else {
            this.boardService.vote(board, member);
        }
        return String.format("redirect:/board/%s", id);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Board>> filterBoardByCategories(@RequestParam("categories") List<String> categories) {
        // 선택된 카테고리 정보(categories)를 이용하여 게시물 필터링 로직을 수행
        List<Board> filteredBoards = boardService.filterByCategories(categories);

        // 필터링된 결과를 JSON 형태로 응답
        return ResponseEntity.ok(filteredBoards);
    }

//    @GetMapping
//    public String getFilteredBoards(@RequestParam(defaultValue = "0") int page,
//                                    @RequestParam(defaultValue = "Latest") String sort,
//                                    Model model) {
//        Pageable pageable = PageRequest.of(page, 10);
//        Page<Board> boards;
////
////        if ("Popular".equals(sort)) {
////            boards = boardService.getBoardsByVoterSize(pageable);
////        } else {
////            boards = boardService.getBoardsByCreateDate(pageable);
////        }
//
////        model.addAttribute("boards", boards);
//        return "boardList";
//    }

    @GetMapping("/date")
    public String getBoardsOrderByDate(Model model, @RequestParam(value = "page", defaultValue = "0")int page) {
        Page<Board> board = boardService.getBoardsByCreateDate(page);
        model.addAttribute("boards", board);
        model.addAttribute("sort", "date");
        return "boardList_form";
    }

    @GetMapping("/popular")
    public String getBoardsOrderByPopular(Model model, @RequestParam(value = "page", defaultValue = "0")int page) {
        Page<Board> board = boardService.getBoardsByVoterSize(page);
        model.addAttribute("boards", board);
        model.addAttribute("sort", "popular");
        return "boardList_form";
    }
}
