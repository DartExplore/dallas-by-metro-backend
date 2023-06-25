package com.dallasbymetro.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "DallasByMetro",
                version = "v0.1",
                description = " backend services for DallasByMetro.com",
                license = @License(
                        name = "MIT License",
                        url = "https://github.com/DartExplore/dallas-by-metro-backend/blob/main/LICENSE"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local ENV"
                ),
                @Server(
                        url = "https://api.dallasbymetro.com",
                        description = "PROD ENV"
                )
        }
)
@SecurityScheme(
        name = "API Key",
        description = "The API Key for CUD operations",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-API-KEY"
)
public class OpenApiConfig {
}

