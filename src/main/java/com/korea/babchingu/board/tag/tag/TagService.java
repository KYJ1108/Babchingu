package com.korea.babchingu.board.tag.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Tag saveTag(String name){
        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }
}