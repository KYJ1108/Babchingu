package com.korea.babchingu.profile;

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

    @GetMapping("/profile1")
    public String getProfile() {
        return "profile";
    }

    @GetMapping("/profile/{id}")
    public String getProfileById(@PathVariable("id") Long id, Model model){

        Profile profile = profileService.getProfileById(id);
        if (profile == null) {
            return "redirect:/error";
        }

        model.addAttribute("nickname", profile.getNickname());
        model.addAttribute("image", profile.getImage());
        model.addAttribute("sex", profile.getSex());
        model.addAttribute("memberId", profile.getMember_id());
        model.addAttribute("profileId", profile.getProfile_id());
        model.addAttribute("phone", profile.getPhone());
        model.addAttribute("email", profile.getEmail());


        return "profile";  // profile.html
    }
    //@PostMapping("/profile")
    //public String postProfile(ProfileForm profileForm) {
    //return "profile";
    //}

    @PostMapping("/profile")
    public String updateProfile(ProfileForm profileForm) {

        profileService.saveProfileById(profileForm);
        return "profile";
    }

    @DeleteMapping("/profile")
    public String deleteProfile(ProfileForm profileForm) {
        return "profile";
    }

}

