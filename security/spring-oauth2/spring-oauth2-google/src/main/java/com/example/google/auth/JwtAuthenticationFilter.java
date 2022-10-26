package com.example.google.auth;

import com.example.google.auth.AuthenticationTokenUtil.AuthUser;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String TOKEN_PARAM = "authToken";
    private static final String TOKEN_HEADER = "X-Auth-Token";


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationTokenUtil authenticationTokenUtil;

    public JwtAuthenticationFilter(String filterProcessingUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(filterProcessingUrl));
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("TokenAuthenticationFilter");
        String token = getToken(request);
        if (StringUtils.isBlank(token)) {
            log.debug("attemptAuthentication: token is empty. uri:{}", request.getRequestURI());
            return null;
        }

        AuthUser authUser;
        try {
            authUser = authenticationTokenUtil.parseToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(authUser.getUsername());
            if (!authenticationTokenUtil.validateToken(token, userDetails))
                throw new JwtAuthenticationException("invalid token");

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(authUser, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.info("authenticated user->{} , setting security context", authUser );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;

        } catch (RuntimeException e) {
            log.info("error:{}", e.toString());
            throw new JwtAuthenticationException("invalid token", e);
        }
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN_PARAM);
        }

        return token;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String token = getToken(request);
        if (StringUtils.isBlank(token)) {
            log.debug("doFilter: token is empty. uri:{}", request.getRequestURI());
            chain.doFilter(request, response);
            return;
        }
        super.doFilter(req, res, chain);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }


}
