package jhkim105.tutorials.spring.security.form_login.config;

import static jhkim105.tutorials.spring.security.form_login.UriConstants.VERSION;

import jhkim105.tutorials.spring.security.form_login.security.CustomAuthenticationEntryPoint;
import jhkim105.tutorials.spring.security.form_login.security.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

  private static final String[] IGNORE_URI_PATTERNS = {
      VERSION
  };

  /**
   * Security Filter 를 태우지 않기 위해 아래 설정을 사용한다
   * 서버 실행하면 다음과 같은 WARN message 가 발생
   * You are asking Spring Security to ignore Mvc [pattern='/version']. This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.
   * @return
   */
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(IGNORE_URI_PATTERNS);
  }

  /**
   *
   * permitAll() 설정
   */
//  @Bean
//  @Order(0)
  public SecurityFilterChain ignoredPatternFilterChain(HttpSecurity http) throws Exception {
    return http
        .securityMatchers((matchers) -> matchers
            .requestMatchers(IGNORE_URI_PATTERNS)
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        )
        .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
        .requestCache(RequestCacheConfigurer::disable)
        .securityContext(AbstractHttpConfigurer::disable)
        .sessionManagement(AbstractHttpConfigurer::disable)
        .build();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("/", "/home", "/login").permitAll()
            .anyRequest().authenticated())
        .formLogin(FormLoginConfigurer::permitAll)
        .logout(LogoutConfigurer::permitAll)
        .exceptionHandling((it) -> it.authenticationEntryPoint(new CustomAuthenticationEntryPoint("/login")));

    http.sessionManagement(session -> session
        .maximumSessions(1)
//        .maxSessionsPreventsLogin(true)
        .expiredUrl("/login?expired"));

    return http.build();
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

  @Bean
  protected UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}