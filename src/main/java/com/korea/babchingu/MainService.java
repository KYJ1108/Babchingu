package com.korea.babchingu;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardService;
import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final BoardService boardService;
    private final MemberService memberService;
//    private final TagService tagService;

    public List<Board> getBoardSearchList(String keyword) {
        return boardService.getSearchList(keyword);
    }

    public List<Member> getMemberSearchList(String keyword) {
        return memberService.getSearchList(keyword);
    }

    public List<Board> getPopularBoards() {
        return boardService.getPopularBoards();
    }
}
