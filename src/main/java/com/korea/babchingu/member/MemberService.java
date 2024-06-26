package com.korea.babchingu.member;

import com.korea.babchingu.DataNotFoundException;
import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardRepository;
import com.korea.babchingu.follow.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.*;

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
            List<Board> memberPosts = boardRepository.findByMember(member);
            memberPosts.sort(Comparator.comparing(Board::getCreateDate).reversed()); // 최신순으로 정렬
            return memberPosts;
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

    public Member getMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null);
    }

    public void saveProfileImage(Member member, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // 파일을 저장할 절대 경로 설정
            ClassPathResource classPathResource = new ClassPathResource("static/profile-images");
            String path = classPathResource.getPath();

            // 디렉토리 생성 (이미 존재하면 생성하지 않음)
            System.out.println("path : " + path);
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 기존 프로필 이미지 삭제
            String currentImageUrl = member.getUrl();
            if (currentImageUrl != null && !currentImageUrl.isEmpty()) {
                String currentImagePath = path + currentImageUrl.substring(currentImageUrl.lastIndexOf("/"));
                File currentImageFile = new File(currentImagePath);
                if (currentImageFile.exists()) {
                    currentImageFile.delete();
                }
            }

            // 파일명 생성
            String fileName = UUID.randomUUID().toString() + "." + file.getContentType().split("/")[1];
            String filePath = path + "/" + fileName;

            // 파일 저장
            try {
                Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // 파일 저장 중 문제가 발생한 경우 예외 처리
                throw new IOException("Failed to save profile image: " + e.getMessage());
            }

            // 파일 저장이 성공한 경우, Member 객체의 URL 업데이트
            String imageUrl = "http://babchingu.kro.kr:8080/profile-images/" + fileName;
            member.setUrl(imageUrl);
        }
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).orElseThrow();
    }

    public void changePassword(String member, String oldPassword, String newPassword) throws CustomException{
        Optional<Member> userOptional = memberRepository.findByLoginId(member);
        if (userOptional.isPresent()){
            Member mem = userOptional.get();
            if (passwordEncoder.matches(oldPassword, mem.getPassword())){
                mem.setPassword(passwordEncoder.encode(newPassword));
                memberRepository.save(mem);
            }else{
                throw new CustomException(ErrorCode.INVALID_PASSWORD);
            }
        }else{
            throw new DataNotFoundException("User not found");
        }
    }

    public List<Member> findChatMembers(Long memberId) {
        return memberRepository.findChatMembers(memberId);
    }

    public List<Member> findChatMembersByPrincipal(Principal principal) {
        Optional<Member> member = memberRepository.findByLoginId(principal.getName());
        if (member.isPresent()) {
            return memberRepository.findChatMembers(member.get().getId());
        } else {
            throw new NoSuchElementException("No member found for the given principal: " + principal.getName());
        }
    }

    public String temp_save(MultipartFile file) {
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
}
