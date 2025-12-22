package com.lucky.projects.lovable_clone.service;



import com.lucky.projects.lovable_clone.Entity.ProjectMember;
import com.lucky.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.lucky.projects.lovable_clone.dto.member.MemberResponse;

import java.util.List;

public interface ProjectMemberService {
    List<ProjectMember> getProjectMembers(Long projectId, Long userId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId);

    MemberResponse updateMemberRole(Long projectId, Long memberId, InviteMemberRequest request, Long userId);

    MemberResponse deleteProjectMember(Long projectId, Long memberId, Long userId);
}
