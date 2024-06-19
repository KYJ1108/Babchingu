package com.korea.babchingu.follow;


import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowRepository extends JpaRepository<Follow, Long> , FollowCustom {

}
