package com.lucky.projects.lovable_clone.dto.auth;

public record UserProfileResponse(
        Long id,
        String username,
        String name
) {
}
