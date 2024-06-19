package com.korea.babchingu.member;

import com.korea.babchingu.board.Board;
import com.korea.babchingu.board.BoardService;
import com.korea.babchingu.security.MyUserDetailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        List<Member> memberList = memberService.findAll();
        return memberList;
    }

    @GetMapping("/signup")
    public String signup(MemberForm memberForm) {
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

    @PostMapping("login")
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
        return "redirect:/"; // 또는 다른 페이지로 리다이렉트
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

        return "redirect:/?memberId=%s".formatted(memberId);
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

        return "profile";
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
                                @RequestParam("file") MultipartFile file,
                                @RequestParam(value = "url", defaultValue = "") String url) {

        // Principal 객체가 null인지 확인
        if (principal == null) {
            // 처리할 방법을 결정하거나 예외를 처리합니다.
            return "redirect:/login"; // 로그인 페이지로 리다이렉트 또는 오류 처리
        }

        // 현재 로그인한 사용자의 아이디를 가져옴
        String memberId = principal.getName();
        Member member = memberService.getMember(memberId);
        String memberImage = member.getUrl();

        // 파일이 업로드된 경우에만 처리
        if (!file.isEmpty() && file.getContentType() != null && file.getContentType().startsWith("image")) {
            // 이미지를 서버에 저장하고 url을 얻음
            String tempUrl = memberService.temp_url(file);
            if (tempUrl != null) {
                url = tempUrl;
                // 사용자 정보에 프로필 사진 URL 저장
                memberService.saveimage(member, url);
            } else {
                model.addAttribute("error", "파일 업로드에 실패했습니다.");
                return "modifyProfile_form";
            }
        } else {
            // 파일이 업로드되지 않은 경우, 이미지 URL을 기존 값으로 설정
            url = memberImage;
        }

        try {
            memberService.updateProfile(member, nickname, email, url);
            return "redirect:/profile";
        } catch (CustomException e) {
            e.printStackTrace();
            model.addAttribute("error", "프로필 업데이트에 실패했습니다: " + e.getMessage());
            return "modifyProfile";
        }
    }

}
