package com.korea.babchingu.tag.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

}
