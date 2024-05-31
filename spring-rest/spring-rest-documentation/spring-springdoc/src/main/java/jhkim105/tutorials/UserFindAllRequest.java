package jhkim105.tutorials;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public record UserFindAllRequest(

    @Schema(nullable = true, description = "검색할 유저 아이디")
    String username
) {

}
