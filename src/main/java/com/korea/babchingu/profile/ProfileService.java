package com.korea.babchingu.profile;

import com.korea.babchingu.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    @Autowired
    private final ProfileRepository profileRepository;

    public Profile getProfileById(Long id) {

        //Map<String, Object> returnMap = new HashMap<>();

        Optional<Profile> profile = profileRepository.findById(id);

        if (profile.isPresent()) {
            return profile.get();
        } else {
            throw new DataNotFoundException("profile not found");
        }
    }

    public void save(Long profileId, String nickname, byte[] image, String sex, String phone) {
        Profile profile = profileRepository.findById(profileId).orElseThrow();

        profile.setNickname(nickname);
        profile.setImage(image);
        profile.setSex(sex);
        profile.setPhone(phone);

        profileRepository.save(profile);
    }
}
