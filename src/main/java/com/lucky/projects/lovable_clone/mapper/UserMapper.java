package com.lucky.projects.lovable_clone.mapper;


import com.lucky.projects.lovable_clone.Entity.User;
import com.lucky.projects.lovable_clone.dto.auth.SignupRequest;
import com.lucky.projects.lovable_clone.dto.auth.UserProfileResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SignupRequest signupRequest);

    UserProfileResponse toUserProfileResponse(User user);
}
