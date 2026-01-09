package com.lucky.projects.lovable_clone.Entity;


import com.lucky.projects.lovable_clone.Enum.ProjectRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProjectMember {

    @EmbeddedId
    ProjectMemberId id;

    @ManyToOne
    @MapsId("projectId")
    Project project;

    @ManyToOne
    @MapsId("userId")
    User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ProjectRole role;

    Instant invitedAt;
    Instant acceptedAt;
}
