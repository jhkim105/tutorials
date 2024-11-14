package jhkim105.tutorials.security.tfa.security;

import jhkim105.tutorials.security.tfa.user.User;
import jhkim105.tutorials.security.tfa.user.UserRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.util.StringUtils;


@Setter
@Slf4j
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

  private UserRepository userRepository;

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    UserPrincipal userPrincipal = (UserPrincipal)getUserDetailsService().loadUserByUsername(authentication.getName());
    String securityCode = ((CustomWebAuthenticationDetails) authentication.getDetails()).getSecurityCode();
    if (StringUtils.isEmpty(securityCode)) {
      super.additionalAuthenticationChecks(userDetails, authentication);
      if (userPrincipal.isUsing2FA()) {
        generateSecurityCode(userPrincipal.getId());
        throw new TwoFactorAuthenticationRequiredException(userPrincipal.getUsername(), "2FA required.");
      }
    } else {
      if (userPrincipal.isUsing2FA()) {
        checkSecurityCode(userPrincipal, securityCode);
      } else {
        throw new BadCredentialsException("Invalid Access");
      }
    }

  }

  private void generateSecurityCode(String userId) {
    User user = userRepository.findById(userId).get();
    // 코드 생성하고, 메일/문자 발송
    // 테스트를 위해서 111111 로 고정
    user.setSecurityCode("111111");
    userRepository.save(user);
  }

  private void checkSecurityCode(UserPrincipal userPrincipal, String securityCode) {
    User user = userRepository.findById(userPrincipal.getId()).get();
    if (user.getSecurityCode() == null || !user.getSecurityCode().equals(securityCode)) {
      throw new TwoFactorAuthenticationInvalidException("Invalid security code");
    }
  }


}
