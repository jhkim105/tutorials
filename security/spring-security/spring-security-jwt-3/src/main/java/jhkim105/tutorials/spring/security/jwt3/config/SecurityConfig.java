package jhkim105.tutorials.spring.security.jwt3.config;


import jhkim105.tutorials.spring.security.jwt3.security.AuthenticationErrorHandler;
import jhkim105.tutorials.spring.security.jwt3.security.JwtAuthenticationFilter;
import jhkim105.tutorials.spring.security.jwt3.security.JwtAuthenticationProvider;
import jhkim105.tutorials.spring.security.jwt3.security.JwtAuthenticationTokenService;
import jhkim105.tutorials.spring.security.jwt3.security.TokenAuthenticationEntryPoint;
import jhkim105.tutorials.spring.security.jwt3.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


  private final AuthenticationErrorHandler authenticationErrorHandler;
  private final JwtAuthenticationTokenService jwtAuthenticationTokenService;


  // spring 에서는 권장하지 않음
  // You are asking Spring Security to ignore Mvc [pattern='/favicon.ico']. This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web
        .ignoring()
        .requestMatchers("/favicon.ico");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .httpBasic().disable()
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/users/join").permitAll()
            .anyRequest().permitAll()
        )
        .addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class)
        .exceptionHandling().authenticationEntryPoint(tokenAuthenticationEntryPoint());

    return http.build();
  }

  private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
        "/**", jwtAuthenticationTokenService, authenticationErrorHandler);
    jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
    jwtAuthenticationFilter.afterPropertiesSet();
    return jwtAuthenticationFilter;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint() {
    return new TokenAuthenticationEntryPoint(authenticationErrorHandler);
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    AuthenticationManager authenticationManager = new ProviderManager(daoAuthenticationProvider(),
        jwtAuthenticationProvider());

    return authenticationManager;
  }

  private DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }


  private JwtAuthenticationProvider jwtAuthenticationProvider() {
    return new JwtAuthenticationProvider();
  }

}
