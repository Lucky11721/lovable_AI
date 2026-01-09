package com.lucky.projects.lovable_clone.ServiceImpl;

import com.lucky.projects.lovable_clone.Entity.Project;
import com.lucky.projects.lovable_clone.Entity.ProjectMember;
import com.lucky.projects.lovable_clone.Entity.ProjectMemberId;
import com.lucky.projects.lovable_clone.Entity.User;
import com.lucky.projects.lovable_clone.Enum.ProjectRole;
import com.lucky.projects.lovable_clone.dto.project.ProjectRequest;
import com.lucky.projects.lovable_clone.dto.project.ProjectResponse;
import com.lucky.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.lucky.projects.lovable_clone.error.ResourceNotFoundException;
import com.lucky.projects.lovable_clone.mapper.ProjectMapper;
import com.lucky.projects.lovable_clone.repository.ProjectMemberRepository;
import com.lucky.projects.lovable_clone.repository.ProjectRepository;
import com.lucky.projects.lovable_clone.repository.UserRepository;
import com.lucky.projects.lovable_clone.security.AuthUtil;
import com.lucky.projects.lovable_clone.service.ProjectService;
import lombok.RequiredArgsConstructor; // [1] Import this
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor // [2] Add this to inject dependencies
public class ProjectServiceImpl implements ProjectService {

    // [3] Mark all dependencies as 'private final'
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberRepository projectMemberRepository;
    private final AuthUtil authUtil;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Long userId = authUtil.getCurrentUserId();
        User owner = userRepository.getReferenceById(userId);

        Project project = Project.builder()
                .name(request.name())
                .isPublic(false)
                .build();
        project = projectRepository.save(project);

        ProjectMemberId projectMemberId = new ProjectMemberId(project.getId(), owner.getId());
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .role(ProjectRole.OWNER)
                .user(owner)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .project(project)
                .build();
        projectMemberRepository.save(projectMember);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects() {
        Long userId = authUtil.getCurrentUserId();
        var projects = projectRepository.findAllAccessibleByUser(userId);
        return projectMapper.toListOfProjectSummaryResponse(projects);
    }

    @Override
    @PreAuthorize("@security.canViewProject(#projectId)")
    public ProjectResponse getUserProjectById(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    // [4] Changed argument name from 'id' to 'projectId' to match annotation
    @PreAuthorize("@security.canEditProject(#projectId)")
    public ProjectResponse updateProject(Long projectId, ProjectRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        project.setName(request.name());
        project = projectRepository.save(project);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    // [5] Changed argument name from 'id' to 'projectId' to match annotation
    @PreAuthorize("@security.canDeleteProject(#projectId)")
    public void softDelete(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    public Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectsByID(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));
    }
}