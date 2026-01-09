package com.lucky.projects.lovable_clone.dto.member;


import com.lucky.projects.lovable_clone.Enum.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String username,
        String name,
        ProjectRole projectRole,
        Instant invitedAt
) {
}
