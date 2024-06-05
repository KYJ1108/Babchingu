package com.korea.babchingu.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/create")
    public String create(BoardForm boardForm) {
        return "board_form";
    }

    @PostMapping("/create")
    public String create(@Valid BoardForm boardForm, BindingResult bindingResult, @RequestParam("images") List<MultipartFile> images) {
        if (bindingResult.hasErrors()) {
            return "board_form";
        }

        boardService.create(boardForm.getTitle(), boardForm.getContent(), images);

        return "redirect:/";
    }


}
