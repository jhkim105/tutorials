package jhkim105.tutorials.spring.security.jwt.config;


import jhkim105.tutorials.spring.security.jwt.security.AuthenticationErrorHandler;
import jhkim105.tutorials.spring.security.jwt.security.JwtAuthenticationFilter;
import jhkim105.tutorials.spring.security.jwt.security.JwtAuthenticationProvider;
import jhkim105.tutorials.spring.security.jwt.security.JwtAuthenticationTokenService;
import jhkim105.tutorials.spring.security.jwt.security.TokenAccessDeniedHandler;
import jhkim105.tutorials.spring.security.jwt.security.TokenAuthenticationEntryPoint;
import jhkim105.tutorials.spring.security.jwt.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final AuthenticationErrorHandler authenticationErrorHandler;
  private final JwtAuthenticationTokenService jwtAuthenticationTokenService;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/error"
        , "/h2-console", "/h2-console/**"
        , "/favicon.ico");
  }

  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .httpBasic().disable()
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
          .antMatchers("/login", "/users/join").permitAll()
          .anyRequest().authenticated()
          .and()
        .addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class)
        .authenticationProvider(tokenAuthenticationProvider())
        .exceptionHandling()
          .authenticationEntryPoint(tokenAuthenticationEntryPoint())
          .accessDeniedHandler(tokenAccessDeniedHandler())
          .and()
        .csrf().disable();
    // @formatter:on
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider()).authenticationProvider(tokenAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public UserDetailsServiceImpl userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean(),
        "/**", jwtAuthenticationTokenService, authenticationErrorHandler);
    return jwtAuthenticationFilter;
  }

  @Bean
  public TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint() {
    return new TokenAuthenticationEntryPoint(authenticationErrorHandler);
  }

  @Bean
  public JwtAuthenticationProvider tokenAuthenticationProvider() {
    return new JwtAuthenticationProvider();
  }

  private TokenAccessDeniedHandler tokenAccessDeniedHandler() {
    return new TokenAccessDeniedHandler(authenticationErrorHandler);
  }

}
