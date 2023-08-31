package jhkim105.tutorials.security.tfa.security;

import jhkim105.tutorials.security.tfa.security.handler.LoginFailureHandler;
import jhkim105.tutorials.security.tfa.security.handler.LoginSuccessHandler;
import jhkim105.tutorials.security.tfa.user.Role;
import jhkim105.tutorials.security.tfa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final LoginSuccessHandler loginSuccessHandler;
  private final LoginFailureHandler loginFailureHandler;
  private final UserRepository userRepository;
  private final CustomWebAuthenticationDetailsSource customWebAuthenticationDetailsSource;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(requests -> requests
            .antMatchers("/", "/home", "/login", "/login_code").permitAll()
            .antMatchers("/admin").hasAuthority(Role.ADMIN.name())
            .anyRequest().authenticated())
//        .formLogin(FormLoginConfigurer::permitAll)
        .formLogin(formLogin -> formLogin.permitAll()
            .authenticationDetailsSource(customWebAuthenticationDetailsSource)
            .successHandler(loginSuccessHandler)
            .failureHandler(loginFailureHandler))
        .logout(LogoutConfigurer::permitAll);

    return http.build();
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
  public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
    CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserRepository(userRepository);
    return authenticationProvider;
  }



}