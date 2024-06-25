package com.korea.babchingu.board;

import com.korea.babchingu.DataNotFoundException;
import com.korea.babchingu.image.Image;
import com.korea.babchingu.image.ImageRepository;
import com.korea.babchingu.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ResourceLoader resourceLoader;
    private final ImageRepository imageRepository;

    @Transactional
    public Board create(String title, String content, List<MultipartFile> images, String address, String jibun, String restName,  Set<String> categories, Member member, LocalDateTime createDate) {
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setAddress(address);
        board.setJibun(jibun);
        board.setRestName(restName);
        board.setCategories(categories);
        board.setMember(member);
        board.setCreateDate(LocalDateTime.now());


        List<Image> imageEntities = new ArrayList<>();

        for (MultipartFile image : images) {
            Image img = new Image();
            img.setBoard(board);
            img.setUrl(storeImage(image)); // 이미지를 저장하고 URL을 설정하는 메서드 호출 (아래에서 구현 필요)

            imageEntities.add(img); // 이미지를 이미지 엔티티 리스트에 추가
        }

        // Board 엔티티에 이미지 리스트를 설정
        board.setImages(imageEntities);

        // 이미지 엔티티들을 저장
        imageRepository.saveAll(imageEntities);

        boardRepository.save(board);
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

    @Transactional
    public Board update(Long id, String title, String content, List<MultipartFile> images, String address, String jibun, String restName, Set<String> categories, LocalDateTime updateDate) {
        Board board = getBoard(id);
        board.setTitle(title);
        board.setContent(content);
        board.setAddress(address);
        board.setJibun(jibun);
        board.setRestName(restName);
        board.setCategories(categories);
        board.setUpdateDate(LocalDateTime.now());


        List<Image> imageEntities = new ArrayList<>();
        if (images == null && images.isEmpty()) {
            for (MultipartFile image : images) {
                Image img = new Image();
                img.setBoard(board);
                img.setUrl(storeImage(image)); // 이미지를 저장하고 URL을 설정하는 메서드 호출 (아래에서 구현 필요)

                imageEntities.add(img); // 이미지를 이미지 엔티티 리스트에 추가
            }
        }


        // Board 엔티티에 이미지 리스트를 설정
        board.setImages(imageEntities);

        // 이미지 엔티티들을 저장
        imageRepository.saveAll(imageEntities);

        boardRepository.save(board);
        return board;
    }

    public void imageDelete(Long deleteImageId) {
        if (deleteImageId != null) {
            imageRepository.deleteById(deleteImageId);
        }
    }

    public void delete(Long id) {
        Board board = getBoard(id);
        boardRepository.delete(board);
    }

    public void vote(Board board, Member member) {
        board.getVoter().add(member);
        this.boardRepository.save(board);
    }

    public void cancelVote(Board board, Member member) {
        Set<Member> updatedVoters = new HashSet<>(board.getVoter());
        updatedVoters.remove(member);
        board.setVoter(updatedVoters);
        this.boardRepository.save(board);
    }

    // 인기 있는 게시물 가져오는 메소드 (예시)
    public List<Board> getPopularBoards() {
        // 좋아요를 기준으로 상위 3개의 게시물 가져오기
        return boardRepository.findTop3ByOrderByVoterDesc(); // 리포지토리에 정의된 메소드 예시
    }

    public List<Board> filterByCategories(List<String> categories) {
        // categories가 비어있을 경우 모든 게시물 반환
        if (categories == null || categories.isEmpty()) {
            return boardRepository.findAll();
        }

        // 선택된 카테고리들로 필터링된 게시물 조회
        return boardRepository.findByCategoriesIn(categories);
    }

//    public Page<Board> getBoardsByCreateDate(Pageable pageable) {
//        return boardRepository.findAllByOrderByCreateDateDesc(pageable);
//    }
//
//    public Page<Board> getBoardsByVoterSize(Pageable pageable) {
//        return boardRepository.findAllByOrderByVoterSizeDesc(pageable);
//    }

    public Page<Board> getBoardsByCreateDate(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return boardRepository.findAllByOrderByCreateDateDesc(pageable);
    }

    public Page<Board> getBoardsByVoterSize(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return boardRepository.findAllByOrderByVoterSizeDesc(pageable);
    }
}
