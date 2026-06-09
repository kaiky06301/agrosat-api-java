package br.com.fiap.agrosat.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME = "bearerAuth";

    @Bean
    public OpenAPI agroSatOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AgroSat API")
                        .description("API de agricultura de precisao - Global Solution FIAP 2026/1 (Java Advanced). "
                                + "Cruza dados de satelite (NDVI, umidade, chuva) com sensores ESP32 no campo "
                                + "e aciona irrigacao/alertas. Autentique-se em POST /api/auth/login e use o token "
                                + "no botao Authorize.")
                        .version("1.0.0")
                        .license(new License().name("FIAP - uso academico")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME))
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME,
                        new SecurityScheme()
                                .name(SECURITY_SCHEME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
