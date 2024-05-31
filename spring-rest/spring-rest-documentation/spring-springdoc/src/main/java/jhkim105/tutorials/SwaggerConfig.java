package jhkim105.tutorials;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final BuildProperties buildProperties;

    public static final String SWAGGER_SECURITY_SCHEME_KEY = "AUTH-TOKEN";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info())
                .components(components());
    }

    private Info info() {
        return new Info()
                .title("API")
                .description("API Document")
                .version(buildProperties.getVersion());
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(SWAGGER_SECURITY_SCHEME_KEY, securityScheme());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .name(HttpHeaders.AUTHORIZATION)
                .in(SecurityScheme.In.HEADER);
    }


//    @Bean
//    public OperationCustomizer globalOperationCustomizer() {
//        return (operation, handlerMethod) -> {
//            operation.setOperationId(handlerMethod.getBeanType().getSimpleName() + "." + handlerMethod.getMethod().getName());
//            return operation;
//        };
//    }
}
