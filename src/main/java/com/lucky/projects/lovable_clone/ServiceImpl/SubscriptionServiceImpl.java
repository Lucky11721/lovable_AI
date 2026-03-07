package com.lucky.projects.lovable_clone.ServiceImpl;

import com.lucky.projects.lovable_clone.Entity.Plan;
import com.lucky.projects.lovable_clone.Entity.Subscription;
import com.lucky.projects.lovable_clone.Entity.User;
import com.lucky.projects.lovable_clone.Enum.SubscriptionStatus;
import com.lucky.projects.lovable_clone.dto.subscription.PortalResponse;
import com.lucky.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import com.lucky.projects.lovable_clone.error.ResourceNotFoundException;
import com.lucky.projects.lovable_clone.mapper.SubscriptionMapper;
import com.lucky.projects.lovable_clone.repository.PlanRepository;
import com.lucky.projects.lovable_clone.repository.ProjectMemberRepository;
import com.lucky.projects.lovable_clone.repository.SubscriptionRepository;
import com.lucky.projects.lovable_clone.repository.UserRepository;
import com.lucky.projects.lovable_clone.security.AuthUtil;
import com.lucky.projects.lovable_clone.service.SubscriptionService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class SubscriptionServiceImpl implements SubscriptionService {

    AuthUtil authUtil;
    SubscriptionRepository subscriptionRepository;
    SubscriptionMapper subscriptionMapper;
    UserRepository userRepository;
    PlanRepository planRepository;
    ProjectMemberRepository projectMemberRepository;
    @Override
    public SubscriptionResponse getCurrentSubscription()  {
        Long userId  = authUtil.getCurrentUserId();

        var currectSibscription = subscriptionRepository.findByUserIdAndStatusIn(userId , Set.of(
                SubscriptionStatus.ACTIVE , SubscriptionStatus.PAST_DUE , SubscriptionStatus.TRIALING
        )).orElse(
                new Subscription()
        );

        return subscriptionMapper.toSubscriptionResponse(currectSibscription);

    }



    @Override
    public PortalResponse openCustomerPortal() {
 return null;

    }


    @Override
    public void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId) {
        boolean exists = subscriptionRepository.existsByStripeSubscriptionId(subscriptionId);

        if(exists) return;

        User user = getUser(userId);
        Plan plan   = getPlan(planId);

        Subscription subscription = Subscription.builder()
                .user(user)
                .plan(plan)
                .stripeSubscriptionId(subscriptionId)
                .status(SubscriptionStatus.INCOMPLETE)
                .build();

        subscriptionRepository.save(subscription);


    }

    @Override
    public void updateSubscription(String gatewaySubscriptionId, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId) {
        Subscription subscription = getSubscription(gatewaySubscriptionId);

        boolean hasSubscriptionUpdated = false;
        if(status != null && status != subscription.getStatus()){
            subscription.setStatus(status);
            hasSubscriptionUpdated = true;
        }

        if(periodStart != null && !periodStart.equals(subscription.getCurrentPeriodStart())){
            subscription.setCurrentPeriodStart(periodStart);
            hasSubscriptionUpdated = true;
        }

        if(cancelAtPeriodEnd != null && cancelAtPeriodEnd != subscription.getCancelAtPeriodEnd()) {
            subscription.setCancelAtPeriodEnd(cancelAtPeriodEnd);
            hasSubscriptionUpdated = true;
        }

        if(planId != null && !planId.equals(subscription.getPlan().getId())){
            Plan newPlan = getPlan(planId);
            subscription.setPlan(newPlan);
            hasSubscriptionUpdated = true;
        }


        if(hasSubscriptionUpdated){
            log.debug("Subscription has been updated : {}" , gatewaySubscriptionId);
        }
    }

    @Override
    public void cancelSubscription(String gatewaySubscriptionId) {
        Subscription subscription  = getSubscription(gatewaySubscriptionId);
        subscription.setStatus(SubscriptionStatus.CANCELED);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void renewSubscriptionPeriod(String subId, Instant periodStart, Instant periodEnd) {
        Subscription subscription = getSubscription(subId);

        Instant newStart = periodStart != null ? periodStart : subscription.getCurrentPeriodStart();
        subscription.setCurrentPeriodStart(newStart);
        subscription.setCurrentPeriodEnd(periodEnd);

        if(subscription.getStatus() == SubscriptionStatus.PAST_DUE ||
         subscription.getStatus() == SubscriptionStatus.INCOMPLETE){
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }

        subscriptionRepository.save(subscription);
    }

    @Override
    public void markSubscriptionPastDue(String subId) {
        Subscription subscription = getSubscription(subId);

        if(subscription.getStatus() == SubscriptionStatus.PAST_DUE){
            log.debug("Subscription is already past due , subscriptionId : {}" , subId);
        }

        subscription.setStatus(SubscriptionStatus.PAST_DUE);
        subscriptionRepository.save(subscription);
    }
    private final Integer FREE_TIER_PROJECTS_ALLOWED = 100;
    @Override
    public boolean canCreateNewProject() {
        Long userId = authUtil.getCurrentUserId();
        SubscriptionResponse currentSubscription = getCurrentSubscription();

        int  countOfOwnedProjects = projectMemberRepository.countProjectOwnedByUser(userId);
                if(currentSubscription.plan() == null){
                    return countOfOwnedProjects < FREE_TIER_PROJECTS_ALLOWED;
                }

                return countOfOwnedProjects < currentSubscription.plan().maxProjects();
    }



    ///  these are the utility methods.

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
    }

    private Plan getPlan(Long planId) {
        return planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan", planId.toString()));

    }

    private Subscription getSubscription(String gatewaySubscriptionId) {
        return subscriptionRepository.findByStripeSubscriptionId(gatewaySubscriptionId).orElseThrow(() ->
                new ResourceNotFoundException("Subscription", gatewaySubscriptionId));
    }
}
