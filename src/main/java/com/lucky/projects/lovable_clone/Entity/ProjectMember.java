package com.lucky.projects.lovable_clone.Entity;


import com.lucky.projects.lovable_clone.Enum.ProjectRole;

import java.time.Instant;

public class ProjectMember {

    ProjectMemberId id;

    Project project;

    User user;

    ProjectRole projectRole;

    Instant invitedAt;
    Instant acceptedAt;
}
