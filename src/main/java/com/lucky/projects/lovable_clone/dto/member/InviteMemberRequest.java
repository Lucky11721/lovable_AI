package com.lucky.projects.lovable_clone.dto.member;


import com.lucky.projects.lovable_clone.Enum.ProjectRole;

public record InviteMemberRequest(
        String email,
        ProjectRole role
) {
}
