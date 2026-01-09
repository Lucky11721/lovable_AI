package com.lucky.projects.lovable_clone.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;


public record JwtUserPrinciple(
        Long userId,
        String username,
        List<GrantedAuthority> authorities
) {

}
