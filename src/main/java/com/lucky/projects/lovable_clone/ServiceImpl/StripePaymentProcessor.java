package com.lucky.projects.lovable_clone.ServiceImpl;

import com.lucky.projects.lovable_clone.Entity.Plan;
import com.lucky.projects.lovable_clone.Entity.User;
import com.lucky.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.lucky.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.lucky.projects.lovable_clone.dto.subscription.PortalResponse;
import com.lucky.projects.lovable_clone.error.ResourceNotFoundException;
import com.lucky.projects.lovable_clone.repository.PlanRepository;
import com.lucky.projects.lovable_clone.repository.UserRepository;
import com.lucky.projects.lovable_clone.security.AuthUtil;
import com.lucky.projects.lovable_clone.service.PaymentProcessor;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)

@Service
public class StripePaymentProcessor implements PaymentProcessor {


    PlanRepository planRepository;
    AuthUtil authUtil;
    UserRepository userRepository;

    @Value("${client.url}")
    String frontenedUrl;

    public StripePaymentProcessor(PlanRepository planRepository, AuthUtil authUtil, UserRepository userRepository, @Value("${client.url}") String frontenedUrl) {
        this.planRepository = planRepository;
        this.authUtil = authUtil;
        this.userRepository = userRepository;

        this.frontenedUrl = frontenedUrl;
    }


    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {
        Plan plan =  planRepository.findById(request.planId()).orElseThrow(() ->
            new ResourceNotFoundException("Plan", request.planId().toString())
        );
        Long userId = authUtil.getCurrentUserId();
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl(frontenedUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontenedUrl + "/cancel.html")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPrice(plan.getStripePriceId())
                                .setQuantity(1L)
                                .build()
                )
                .build();



        try {
            System.out.println("Before Stripe call");

            // 1. Enable Debugging
           // Stripe.logLevel = Stripe.LEVEL_DEBUG;

            // 2. Set strict timeouts (fail after 5 seconds instead of waiting forever)
            Stripe.setConnectTimeout(5000); // 5 seconds
            Stripe.setReadTimeout(5000);    // 5 seconds

            long start = System.currentTimeMillis();
            Session session = Session.create(params);
            long end = System.currentTimeMillis();

            System.out.println("Stripe call took: " + (end - start) + " ms");
            return new CheckoutResponse(session.getUrl());

        } catch (StripeException e) {
            // This will now print the EXACT network error
            e.printStackTrace();
            throw new RuntimeException("Stripe connection failed: " + e.getMessage());
        }
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }

    @Override
    public void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata) {

    }
}
