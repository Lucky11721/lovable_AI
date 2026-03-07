package com.lucky.projects.lovable_clone.repository;

import com.lucky.projects.lovable_clone.Entity.Subscription;
import com.lucky.projects.lovable_clone.Enum.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserIdAndStatusIn(Long userId, Set<SubscriptionStatus> active);


    boolean existsByStripeSubscriptionId(String subscriptionId);

     Optional<Subscription>  findByStripeSubscriptionId(String gatewaySubscriptionId);
}
