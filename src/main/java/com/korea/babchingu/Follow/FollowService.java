package com.korea.babchingu.Follow;

import com.korea.babchingu.member.Member;
import com.korea.babchingu.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public List<FollowDTO> followingList(Member selectedMember, Member requestMember) {
        List<Follow> list = followRepository.findByFromMember(selectedMember);
        List<FollowDTO> followList = new ArrayList<>();
        for (Follow f : list) {
            Member toMember = f.getToMember();
            followList.add(new FollowDTO(
                    toMember.getId(),
                    toMember.getProfile().getNickname(),
                    findStatus(toMember, requestMember)
            ));
        }
        return followList;
    }

    public List<FollowDTO> followerList(Member selectedMember, Member requestMember) {
        List<Follow> list = followRepository.findByToMember(selectedMember);
        List<FollowDTO> followerList = new ArrayList<>();
        for (Follow f : list) {
            Member fromMember = f.getFromMember();
            followerList.add(new FollowDTO(
                    fromMember.getId(),
                    fromMember.getProfile().getNickname(),
                    findStatus(fromMember, requestMember)
            ));
        }
        return followerList;
    }

    private boolean findStatus(Member targetMember, Member requestMember) {
        return followRepository.existsByFromMemberAndToMember(requestMember, targetMember);
    }
}

