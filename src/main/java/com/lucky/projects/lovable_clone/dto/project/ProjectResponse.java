package com.lucky.projects.lovable_clone.dto.project;


import com.lucky.projects.lovable_clone.dto.auth.UserProfileResponse;
import jakarta.persistence.NamedStoredProcedureQueries;
import lombok.AllArgsConstructor;

import java.time.Instant;


public record ProjectResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt,
        UserProfileResponse owner
) {

}
