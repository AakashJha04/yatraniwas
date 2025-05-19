package com.example.yatraniwas.service;

import com.example.yatraniwas.dto.ProfileUpdateRequestDto;
import com.example.yatraniwas.dto.UserDto;
import com.example.yatraniwas.entity.User;

public interface UserService {

    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
