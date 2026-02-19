package com.lucky.projects.lovable_clone.dto.member;


import com.lucky.projects.lovable_clone.Enum.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull ProjectRole role)
{
}
