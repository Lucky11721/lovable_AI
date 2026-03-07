package com.lucky.projects.lovable_clone.repository;

import com.lucky.projects.lovable_clone.Entity.Plan;
import com.lucky.projects.lovable_clone.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long > {

    Optional<Plan> findByStripePriceId(String id);
}
