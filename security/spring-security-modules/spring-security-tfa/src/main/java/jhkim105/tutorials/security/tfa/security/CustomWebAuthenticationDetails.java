package jhkim105.tutorials.security.tfa.security;

import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;


@Getter
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private final String securityCode;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        securityCode = request.getParameter("code");
    }

}