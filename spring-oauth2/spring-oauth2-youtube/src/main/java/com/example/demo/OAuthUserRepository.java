package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthUserRepository extends JpaRepository<OAuthUser, String> {


  OAuthUser findByRegistrationIdAndUserId(String registrationId, String userId);
}
