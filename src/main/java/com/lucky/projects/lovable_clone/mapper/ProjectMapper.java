package com.lucky.projects.lovable_clone.mapper;

import com.lucky.projects.lovable_clone.Entity.Project;
import com.lucky.projects.lovable_clone.dto.project.ProjectResponse;
import com.lucky.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse toProjectResponse(Project project);
    @Mapping(target = "name", source = "name")
    ProjectSummaryResponse toProjectSummaryResponse(Project project);

    List<ProjectSummaryResponse> toListOfProjectSummaryResponse(List<Project> projects);

}
