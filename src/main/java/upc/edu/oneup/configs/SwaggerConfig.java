package upc.edu.oneup.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Smart",
                version = "1.0.0",
                description = "Backend COnfiguration for Smart products and services."
        )
)
public class SwaggerConfig {

}
