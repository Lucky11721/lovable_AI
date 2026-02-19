package com.lucky.projects.lovable_clone.security;


import com.lucky.projects.lovable_clone.Enum.ProjectPermission;
import com.lucky.projects.lovable_clone.Enum.ProjectRole;
import com.lucky.projects.lovable_clone.repository.ProjectMemberRepository;
import com.lucky.projects.lovable_clone.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.servlet.FilterRegistration;
import org.springframework.stereotype.Component;

@Component("security")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class SecurityExpression {


    UserRepository userRepository;
    AuthUtil authUtil;
    private final ProjectMemberRepository projectMemberRepository;

    private boolean hasPermission(Long projectId, ProjectPermission projectPermission) {
        Long userId = authUtil.getCurrentUserId();

        return projectMemberRepository.findRoleByProjectIdandUserId(userId, projectId).
                map(role -> role.getPermissions().contains(projectPermission))
                .orElse(false);
    }
    public boolean canViewProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.VIEW);

    }

    public boolean canEditProject(Long projectId){

        return hasPermission(projectId, ProjectPermission.EDIT);
    }

    public boolean canDeleteProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.DELETE);
    }


    public boolean canViewMembers(Long projectId) {
        return hasPermission(projectId, ProjectPermission.VIEW_MEMBERS);
    }

    public boolean canManageMembers(Long projectId) {
        return hasPermission(projectId, ProjectPermission.MANAGE_MEMBERS);
    }
}
