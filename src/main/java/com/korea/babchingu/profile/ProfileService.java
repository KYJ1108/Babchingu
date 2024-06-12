package com.korea.babchingu.profile;

import com.korea.babchingu.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

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

    public int saveProfileById(ProfileForm profileForm){

        Profile profile = new Profile();

        long profileId = Long.parseLong(profileForm.getProfile_id().toString());
        long memberId = Long.parseLong(profileForm.getMember_id().toString());

        profile.setProfile_id(profileId);
        profile.setMember_id(memberId);
        profile.setSex(profileForm.getSex());
        profile.setImage(profileForm.getImage());
        profile.setNickname(profileForm.getNickname());

        profileRepository.save(profile);

        return 0;
    }
}
