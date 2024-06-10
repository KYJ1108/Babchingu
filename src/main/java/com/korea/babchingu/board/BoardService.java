package com.korea.babchingu.board;

import com.korea.babchingu.DataNotFoundException;
import com.korea.babchingu.image.Image;
import com.korea.babchingu.image.ImageRepository;
import com.korea.babchingu.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ResourceLoader resourceLoader;
    private final ImageRepository imageRepository;

    public Board create(String title, String content, List<MultipartFile> images, String address, String jibun, String restName, Member member) {
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setAddress(address);
        board.setJibun(jibun);
        board.setRestName(restName);
        board.setMember(member);

        boardRepository.save(board);

        for (MultipartFile image : images) {
            Image img = new Image();
            img.setBoard(board);
            img.setUrl(storeImage(image)); // 또는 img.setImageData(image.getBytes());

            imageRepository.save(img);
        }
        return board;
    }

    private String storeImage(MultipartFile file) {
        if (!file.isEmpty())
            try {
                String path = resourceLoader.getResource("classpath:/static").getFile().getPath();
                File fileFolder = new File( path + "/image");
                if (!fileFolder.exists())
                    fileFolder.mkdirs();
                String filePath = "/image/" + UUID.randomUUID().toString() + "." + file.getContentType().split("/")[1];
                file.transferTo(Paths.get(path + filePath));
                return filePath;
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        return null;
    }

    public Board getBoard(Long id) {
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("board not found");
        }
    }

    public List<Board> getSearchList(String keyword) {
        return boardRepository.findByTitleContainingIgnoreCaseOrRestNameContainingIgnoreCase(keyword, keyword);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
    public Board update(Long id, String title, String content, List<MultipartFile> images, String address, String jibun, String restName) {
        Board board = getBoard(id);
        board.setTitle(title);
        board.setContent(content);
        board.setAddress(address);
        board.setJibun(jibun);
        board.setRestName(restName);

        boardRepository.save(board);

        for (MultipartFile image : images) {
            Image img = new Image();
            img.setBoard(board);
            img.setUrl(storeImage(image)); // 또는 img.setImageData(image.getBytes());

            imageRepository.save(img);
        }
        return board;
    }

    public void delete(Long id) {
        Board board = getBoard(id);
        boardRepository.delete(board);
    }
}