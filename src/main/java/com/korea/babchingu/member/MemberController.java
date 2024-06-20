package com.korea.babchingu.member;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardService;
import com.korea.babchingu.security.MyUserDetailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MyUserDetailService myUserDetailService;
    private final PasswordEncoder passwordEncoder;
    private final SendEmailService sendEmailService;

    @ModelAttribute("memberList")
    public List<Member> member(){
        return memberService.findAll();
    }

    @GetMapping("/signup")
    public String signupForm(MemberForm memberForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        if (!memberForm.getPassword().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "error.memberForm", "비밀번호가 일치하지 않습니다.");
            return "signup";
        }

        try {
            memberService.save(memberForm.getLoginId(), memberForm.getPassword(), memberForm.getEmail());

        } catch (RuntimeException e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, @RequestParam String username, @RequestParam String password) {
        UserDetails userDetails = myUserDetailService.loadUserByUsername(username);

        if (userDetails == null) {
            // 사용자가 없는 경우에 대한 처리
            model.addAttribute("error", "존재하지 않는 아이디입니다.");
            return "login";
        }

        // 비밀번호가 일치하지 않는 경우에 대한 처리
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "login";
        }

        // 인증 성공한 경우에 대한 처리
        return "redirect:/";
    }

    @GetMapping("/findPw")
    public String findPwForm(MemberPwRequestDto memberPwRequestDto) {
        return "findPw";
    }

    @PostMapping("/findPw")
    public String findPw(@Valid MemberPwRequestDto memberPwRequestDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "아이디와 이메일을 확인해주세요.");
            return "findPw";
        }

        try {
            sendEmailService.createMailAndChargePassword(memberPwRequestDto);
        } catch (Exception | CustomException e) {
            model.addAttribute("error", "비밀번호 재설정에 실패했습니다. 다시 시도해주세요.");
            return "findPw";
        }

        return "redirect:/member/login";
    }

    @PostMapping("/member/search")
    public String search(Model model, @RequestParam String memberId, @RequestParam(value = "isSearchModal", required = false) String isSearchModal) {
        List<Member> searchLoginId = memberService.getSearchList(memberId);
        model.addAttribute("searchLoginId", searchLoginId);
        model.addAttribute("isSearchModal", isSearchModal);

        return "layout";
    }

    @GetMapping("/profile")
    public String userProfile(Principal principal, Model model){
        // 사용자가 로그인되어 있는지 확인
        if (principal == null){
            // 사용자가 로그인되어 있지 않은 경우 처리
            return "redirect:/user/login";
        }

        // 현재 로그인한 사용자의 아이디를 가져옴
        String memberId = principal.getName();

        // memberService를 사용하여 사용자의 정보를 가져옴
        Member member = memberService.getMember(memberId);
        model.addAttribute("member", member);

        // 사용자가 작성간 게시물 등의 정보를 가져옴
        List<Board> memberPosts = memberService.getMemberPosts(member.getLoginId());
        model.addAttribute("memberPosts", memberPosts);

        // 현재 로그인한 사용자와 프로필 주인의 아이디를 비교하여 모델에 추가
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInMemberId = authentication.getName();
        model.addAttribute("loggedInMemberId", loggedInMemberId);

        return "profile";
    }

    @GetMapping("/profile/{loginId}")
    public String userProfile(@PathVariable String loginId, Model model) {
        // 사용자 정보를 로드하는 서비스 메서드를 호출하여 사용자 정보를 가져옴
        Member member = memberService.getMemberByLoginId(loginId);
        if (member == null) {
            // 사용자가 존재하지 않는 경우, 예외 처리 또는 다른 방법으로 처리할 수 있음
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        model.addAttribute("member", member);

        // 사용자의 게시물 등을 가져오는 등의 추가적인 작업을 수행할 수 있음
        List<Board> memberPosts = memberService.getMemberPosts(member.getLoginId());
        model.addAttribute("memberPosts", memberPosts);

        return "profile"; // profile.html로 이동
    }

    @GetMapping("/modifyProfile")
    public String modifyProfile(Model model, Principal principal) {
        // 현재 로그인한 사용자의 아이디를 가져옴
        String memberId = principal.getName();

        // userService를 사용하여 사용자 정보를 가져옴
        Member member = memberService.getMember(memberId);
        String nickname = member.getNickname();
        model.addAttribute("member", member);
        model.addAttribute("nickname", nickname);

        return "modifyProfile";
    }

    @PostMapping("/modifyProfile")
    public String changeProfile(Model model, Principal principal,
                                @RequestParam("nickname") String nickname,
                                @RequestParam("email") String email,
                                @RequestParam("file") MultipartFile file) {
        try {
            // 현재 로그인한 사용자 정보 가져오기
            String memberId = principal.getName();
            Member member = memberService.getMember(memberId);

            // 파일이 업로드된 경우에만 처리
            if (!file.isEmpty()) {
                // 프로필 이미지 저장 및 URL 업데이트
                memberService.saveProfileImage(member, file);
            }

            // 프로필 정보 업데이트
            memberService.updateProfile(member, nickname, email, member.getUrl());

            return "redirect:/profile";
        } catch (IOException e) {
            // 파일 업로드 실패 시 처리
            model.addAttribute("error", "파일 업로드에 실패했습니다.");
            return "modifyProfile";
        } catch (CustomException e) {
            // 다른 예외 처리
            model.addAttribute("error", "프로필 업데이트에 실패했습니다: " + e.getMessage());
            return "modifyProfile";
        }
    }
}
