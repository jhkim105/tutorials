package com.example.demo;

import com.example.demo.OAuthUser.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthUserRepository extends JpaRepository<OAuthUser, String> {


  OAuthUser findByOauthProviderAndUserId(OAuthProvider oauthProvide, String userId);
}
