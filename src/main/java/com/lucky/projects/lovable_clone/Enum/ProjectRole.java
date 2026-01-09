package com.lucky.projects.lovable_clone.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.lucky.projects.lovable_clone.Enum.ProjectPermission.*;

@Getter
public enum ProjectRole {

    // Cleaned up: removed Set.of(), just pass the permissions directly
    EDITOR(VIEW, EDIT, DELETE, VIEW_MEMBERS),
    VIEWER(VIEW, VIEW_MEMBERS),
    OWNER(VIEW, EDIT, DELETE, MANAGE_MEMBERS, VIEW_MEMBERS);

    private final Set<ProjectPermission> permissions;

    // This constructor handles the varargs conversion automatically
    ProjectRole(ProjectPermission... permissions) {
        this.permissions = Set.of(permissions);
    }
}