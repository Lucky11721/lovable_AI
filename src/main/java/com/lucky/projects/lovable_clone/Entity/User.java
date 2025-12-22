package com.lucky.projects.lovable_clone.Entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE) // we do not manually set access specifier it make it default as set in AccessLevel.
public class User {

    Long id;

    String email;
    String passwordHash;
    String name;

    String avatarUrl;
    Instant createdAt;
    Instant updatedAt;

    Instant deletedAt; //soft delete
}

// user information like login details.

