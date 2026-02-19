package com.lucky.projects.lovable_clone.dto.member;


import com.lucky.projects.lovable_clone.Enum.ProjectRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InviteMemberRequest(

        @Email
        @NotNull
        @NotBlank
        String username,

        @NotNull
        ProjectRole role
) {
}
