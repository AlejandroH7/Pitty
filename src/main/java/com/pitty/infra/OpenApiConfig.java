package com.pitty.infra;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pittyOpenAPI() {
        var info = new Info()
                .title("Pitty API")
                .description("API REST del proyecto (Clientes, Ingredientes, Postres, Recetas, Pedidos)")
                .version("v1.0.0")
                .contact(new Contact().name("Equipo Pitty").email("equipo@pitty.local"));

        var localServer = new Server().url("http://localhost:8080").description("Local");
        return new OpenAPI().info(info).servers(List.of(localServer));
    }
}