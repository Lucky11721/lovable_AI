package com.lucky.projects.lovable_clone.mapper;

import com.lucky.projects.lovable_clone.Entity.ProjectMember;
import com.lucky.projects.lovable_clone.Entity.User;
import com.lucky.projects.lovable_clone.dto.member.MemberResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    @Mapping(target = "userId" , source = "id")
    @Mapping(target = "projectRole" , constant = "OWNER")
    MemberResponse toProjectMemberResponseFromOwner(User owner);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username" , source = "user.username")
    @Mapping(target = "name", source = "user.name")
    MemberResponse toProjectMemberResponseFromMember(ProjectMember projectMember);
}
