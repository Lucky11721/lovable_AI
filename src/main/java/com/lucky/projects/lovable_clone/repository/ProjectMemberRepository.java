package com.lucky.projects.lovable_clone.repository;

import com.lucky.projects.lovable_clone.Entity.ProjectMember;
import com.lucky.projects.lovable_clone.Entity.ProjectMemberId;
import com.lucky.projects.lovable_clone.Enum.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember , ProjectMemberId> {

    List<ProjectMember> findByIdProjectId(Long projectId);

    @Query("""
    SELECT pm.role
    FROM ProjectMember pm
    WHERE pm.id.projectId = :projectId
      AND pm.id.userId = :userId
""")
    Optional<ProjectRole> findRoleByProjectIdandUserId(Long userId, Long projectId);
}



