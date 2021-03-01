package com.example.demo;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.util.Assert;

@Slf4j
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private OAuth2AuthorizationRequestResolver defaultResolver;

    public CustomAuthorizationRequestResolver(ClientRegistrationRepository repo, String authorizationRequestBaseUri) {
        defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(repo, authorizationRequestBaseUri);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = defaultResolver.resolve(request);
        if (oAuth2AuthorizationRequest != null) {
            oAuth2AuthorizationRequest = customizeAuthorizationRequest(oAuth2AuthorizationRequest, request);
        }
        return oAuth2AuthorizationRequest;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest req = defaultResolver.resolve(request, clientRegistrationId);
        if (req != null) {
            req = customizeAuthorizationRequest(req, request);
        }
        return req;
    }

    private OAuth2AuthorizationRequest customizeAuthorizationRequest(OAuth2AuthorizationRequest oAuth2AuthorizationRequest,
        HttpServletRequest request) {
        String callback = request.getParameter(StateParameter.CALLBACK_PARAM);
        Assert.hasText(callback, "callback parameter ");
        String state = StateParameter.builder().callback(request.getParameter(StateParameter.CALLBACK_PARAM)).build().generateKey();

        Map<String, Object> additionalParameters =new LinkedHashMap<>(oAuth2AuthorizationRequest.getAdditionalParameters());
        additionalParameters.put("access_type", "offline");
        return OAuth2AuthorizationRequest.from(oAuth2AuthorizationRequest).additionalParameters(additionalParameters).state(state).build();
    }

    @Getter
    @Builder
    public static class StateParameter {
        public static final String CALLBACK_PARAM = "callback";

        private String callback;

        public String generateKey() {
            return Base64.getUrlEncoder().encodeToString(this.callback.getBytes());
        }

        public static StateParameter fromKey(String state) {
            String callback = new String(Base64.getUrlDecoder().decode(state.getBytes()));
            return StateParameter.builder().callback(callback).build();
        }
    }
}