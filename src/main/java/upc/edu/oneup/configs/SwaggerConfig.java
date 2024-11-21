package upc.edu.oneup.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Oneup API",
                version = "1.0.0",
                description = "Documentación de la API de Oneup"
        )
)
public class SwaggerConfig {
}