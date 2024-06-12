package com.korea.babchingu;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final BoardService boardService;
//    private final TagService tagService;

    public List<Board> getSearchList(String keyword) {
        return boardService.getSearchList(keyword);
    }

//    public MainDataDto getDefaultMainData(String keyword){
//        List<Board> boardList = boardService.getTopBoardList();
//
//        if (boardList.isEmpty()) {
//            Board board = this.saveDeraultBoard();
//            boardList.add(board);
//        }
//
//        Board targetBoard = boardList.get(0);
//
//        List<Board> searchedBoardList = boardService.getSearchedBoardList(keyword);
//        List<Tag> tagList = tagService.getTagList();
//
//        MainDataDto mainDataDto = new MainDataDto(boardList, targetBoard, boardList, searchedBoardList, tagList);
//        return mainDataDto;
//;    }
//
//    public MainDataDto getMainData(Long boardId, String keyword, String sort){
//        MainDataDto mainDataDto = this.getDefaultMainData(keyword);
//        Board targetBoard = this.getBoard(boardId);
//
//        mainDataDto.setTargetBoard(targetBoard);
//
//        List<Board> sortedBoardList;
//        if (sort.equals("date")) {
//            if (sort.equals("date")) {
//                sortedBoardList = boardService.getSortedListByCreateDate(targetBoard);
//            }
//            else {
//
//            }
//        }
//    }
}
