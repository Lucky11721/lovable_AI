package com.lucky.projects.lovable_clone.Entity;

import com.lucky.projects.lovable_clone.Enum.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.ConnectionBuilder;
import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor

public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    User user;
    @ManyToOne
    @JoinColumn(name = "plan_id")
    Plan plan;

    @Enumerated(value = EnumType.STRING)
    SubscriptionStatus status;
    String stripeCustomerId;
    String stripeSubscriptionId;

    Instant currentPeriodStart;
    Instant currentPeriodEnd;
    Boolean cancelAtPeriodEnd = false;

    Instant createdAt;
    Instant updatedAt;



}
