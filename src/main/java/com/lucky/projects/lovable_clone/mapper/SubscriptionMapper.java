package com.lucky.projects.lovable_clone.mapper;

import com.lucky.projects.lovable_clone.Entity.Plan;
import com.lucky.projects.lovable_clone.Entity.Subscription;
import com.lucky.projects.lovable_clone.dto.subscription.PlanResponse;
import com.lucky.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    PlanResponse toPlanResponse(Plan plan);
}
