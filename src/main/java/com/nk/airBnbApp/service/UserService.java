package com.nk.airBnbApp.service;


import com.nk.airBnbApp.dto.ProfileUpdateRequestDto;
import com.nk.airBnbApp.dto.UserDto;
import com.nk.airBnbApp.entity.User;

public interface UserService {
    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
