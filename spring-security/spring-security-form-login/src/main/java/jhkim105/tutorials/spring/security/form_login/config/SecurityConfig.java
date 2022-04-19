package jhkim105.tutorials.spring.security.form_login.config;

import jhkim105.tutorials.spring.security.form_login.security.CustomAuthenticationEntryPoint;
import jhkim105.tutorials.spring.security.form_login.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/", "/home").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
//            .loginPage("/login")
            .permitAll()
            .and()
        .logout()
            .permitAll()
            .and()
        .exceptionHandling()
          .authenticationEntryPoint(new CustomAuthenticationEntryPoint("/login"));
  }

  @Bean
  @Override
  protected UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

}