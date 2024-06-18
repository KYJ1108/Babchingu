package com.korea.babchingu.profile;

import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final MemberService memberService;

    @GetMapping("/member/{id}/profile")
    public String profile(@PathVariable("id") Long id, Model model){

        Member member = memberService.findById(id);
        Profile profile = member.getProfile();

        if (profile == null) {
            return "redirect:/error";
        }

        model.addAttribute("profile", profile);
        model.addAttribute("nickname", profile.getNickname());
        model.addAttribute("image", profile.getImage());
        model.addAttribute("sex", profile.getSex());
        model.addAttribute("memberId", member.getLoginId());
        model.addAttribute("profileId", profile.getProfile_id());
        model.addAttribute("phone", profile.getPhone());


        return "profile";  // profile.html
    }
//    @PostMapping("/profile")
//    public String postProfile(ProfileForm profileForm) {
//    return "profile";
//    }

    @PostMapping("/member/{id}/profile/update")
    public String updateProfile(@PathVariable("id") Long id, ProfileForm profileForm) {

        profileService.save(profileForm.getProfile_id(), profileForm.getNickname(), profileForm.getImage(), profileForm.getSex(), profileForm.getPhone());
        return "profile";
    }

    @DeleteMapping("/profile")
    public String deleteProfile(ProfileForm profileForm) {
        return "profile";
    }

}

