package com.korea.babchingu.member;

import com.korea.babchingu.DataNotFoundException;
import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardRepository;
import com.korea.babchingu.follow.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;
    private final ResourceLoader resourceLoader;

    public Member save(String loginId, String password, String email) {
        // 아이디 중복 체크
        if (memberRepository.findByLoginId(loginId).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        // 이메일 중복 체크 (필요시 추가)
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(passwordEncoder.encode(password));
        member.setEmail(email);


        // Member 저장
        member = memberRepository.save(member);
        
        return member;
    }

    public Member getMember(String loginId) {
        Optional<Member> member = this.memberRepository.findByLoginId(loginId);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public List<Member> getSearchList(String keyword) {
        return memberRepository.findByNicknameContainingIgnoreCase(keyword);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    public List<Board> getMemberPosts(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElse(null);
        if (member != null){
            return boardRepository.findByMember(member);
        }
        return Collections.emptyList();
    }

    public void updateProfile(Member member, String nickname, String email, String userImage)throws CustomException {
        // 사용자 정보를 업데이트
        member.setNickname(nickname);
        member.setEmail(email);
        member.setUrl(userImage);
        // 업데이트된 사용자를 데이터베이스에 다시 저장
        memberRepository.save(member);
    }

    public String temp_url(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 파일을 저장할 절대 경로 설정
                String uploadDir = "src/main/resources/static/pimg";

                // 디렉토리 생성 (이미 존재하면 생성하지 않음)
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // 파일명 생성
                String fileName = UUID.randomUUID().toString() + "." + file.getContentType().split("/")[1];
                String filePath = uploadDir + "/" + fileName;

                // 파일 저장
                Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

                return "/pimg/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void saveimage(Member member, String url) {
        try{
            String path = resourceLoader.getResource("classpath:/static").getFile().getPath();
            if(member.getUrl() != null){
                File oldFile = new File(path+member.getUrl());
                if(oldFile.exists()){
                    oldFile.delete();
                }
            }
            member.setUrl(url);
            memberRepository.save(member);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Member getMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null);
    }

    public void saveProfileImage(Member member, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // 파일을 저장할 절대 경로 설정
            String uploadDir = "src/main/resources/static/profile-images";

            // 디렉토리 생성 (이미 존재하면 생성하지 않음)
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 파일명 생성
            String fileName = UUID.randomUUID().toString() + "." + file.getContentType().split("/")[1];
            String filePath = uploadDir + "/" + fileName;

            // 파일 저장
            try {
                Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // 파일 저장 중 문제가 발생한 경우 예외 처리
                throw new IOException("Failed to save profile image: " + e.getMessage());
            }

            // 파일 저장이 성공한 경우, Member 객체의 URL 업데이트
            String imageUrl = "/profile-images/" + fileName;
            member.setUrl(imageUrl);
        }
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).orElseThrow();
    }
}
