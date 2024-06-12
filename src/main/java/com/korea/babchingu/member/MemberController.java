package com.korea.babchingu.member;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MyUserDetailService myUserDetailService;
    private final PasswordEncoder passwordEncoder;
    private final SendEmailService sendEmailService;


    @Getter
    @Setter
    class MemberForm {
        @NotEmpty(message = "아이디를 입력해주세요.")
        private String loginId;
        @NotEmpty(message = "비밀번호를 입력해주세요.")
        private String password;
        @NotEmpty(message = "비밀번호를 동일하게 입력해주세요.")
        private String password2;
        @NotEmpty(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식이 아닙니다.")
        private String email;
    }

    @GetMapping("/signup")
    public String signup(MemberForm memberForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
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
}
