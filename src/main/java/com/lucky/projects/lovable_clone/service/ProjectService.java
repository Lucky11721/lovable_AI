package com.lucky.projects.lovable_clone.service;

import com.lucky.projects.lovable_clone.dto.project.ProjectRequest;
import com.lucky.projects.lovable_clone.dto.project.ProjectResponse;
import com.lucky.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.lucky.projects.lovable_clone.error.ResourceNotFoundException;

import java.util.List;

public interface ProjectService {
    List<ProjectSummaryResponse> getUserProjects();

    ProjectResponse getUserProjectById(Long id);

    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse updateProject(Long id, ProjectRequest request);

    void softDelete(Long id);
}
