package com.korea.babchingu.member;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Getter
    @Setter
    class MemberForm {
        @NotEmpty(message = "아이디를 입력해주세요.")
        private String loginId;
        @NotEmpty(message = "비밀번호를 입력해주세요.")
        private String password;
        @NotEmpty(message = "비밀번호를 동일하게 입력해주세요.")
        private String password2;
    }

    @GetMapping("/signup")
    public String signup(MemberForm memberForm, Model model, String flag) {
        model.addAttribute("flag", "signup");
        return "signup_login";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberForm memberForm, BindingResult bindingResult, Model model) {
        model.addAttribute("flag", "signup");
        if(bindingResult.hasErrors()) {
            return "signup_login";
        }
        if (!memberForm.getPassword().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "error.memberForm", "비밀번호가 일치하지 않습니다.");
            return "signup_login";
        }

        memberService.save(memberForm.getLoginId(), memberForm.getPassword());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(MemberForm memberForm, Model model) {
        model.addAttribute("flag", "signin");
        return "signup_login";
    }
}
