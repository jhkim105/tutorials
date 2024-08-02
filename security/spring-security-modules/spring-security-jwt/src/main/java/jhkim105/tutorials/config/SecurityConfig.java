package jhkim105.tutorials.config;


import jhkim105.tutorials.jwt.JwtService;
import jhkim105.tutorials.security.JwtAuthenticationFilter;
import jhkim105.tutorials.security.JwtAuthenticationProvider;
import jhkim105.tutorials.security.SecurityErrorHandler;
import jhkim105.tutorials.security.TokenAuthenticationEntryPoint;
import jhkim105.tutorials.security.UserDetailsServiceImpl;
import jhkim105.tutorials.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
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

  private static final String[] IGNORE_URI_PATTERNS = {
      "/version", "/error"
  };

  private final SecurityErrorHandler securityErrorHandler;
  private final JwtService jwtService;
  private final UserRepository userRepository;


  // spring 에서는 권장하지 않음
  // You are asking Spring Security to ignore Mvc [pattern='/favicon.ico']. This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead
//  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web
        .ignoring()
        .requestMatchers(IGNORE_URI_PATTERNS);
  }

  /**
   *
   * permitAll() 설정
   */
  @Bean
  @Order(1)
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
    http
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .sessionManagement((it) -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/jwks/**").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class)
        .exceptionHandling((it) -> it.authenticationEntryPoint(tokenAuthenticationEntryPoint()));

    return http.build();
  }

  private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
        "/**", jwtService, securityErrorHandler);
    jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
    jwtAuthenticationFilter.afterPropertiesSet();
    return jwtAuthenticationFilter;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl(userRepository);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint() {
    return new TokenAuthenticationEntryPoint(securityErrorHandler);
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    AuthenticationManager authenticationManager = new ProviderManager(daoAuthenticationProvider(), jwtAuthenticationProvider());
    return authenticationManager;
  }

  private JwtAuthenticationProvider jwtAuthenticationProvider() {
    return new JwtAuthenticationProvider();
  }

  private DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }



}
