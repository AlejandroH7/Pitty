// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package com.pitty.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
   info = @Info(
   title = "Pitty API",
   version = "v1",
   description = "API REST del proyecto Pitty",
   contact = @Contact(
   name = "Equipo Pitty",
   email = "equipo@pitty.local"
),
   license = @License(
   name = "MIT"
)
),
   servers = {@Server(
   url = "http://localhost:8080",
   description = "Local"
)}
)
@Configuration
public class OpenApiConfig {
   public OpenApiConfig() {
   }

   @Bean
   public GroupedOpenApi apiGroup() {
      return GroupedOpenApi.builder().group("api").pathsToMatch(new String[]{"/api/**"}).packagesToScan(new String[]{"com.pitty.controller"}).build();
   }

   @Bean
   public ExternalDocumentation externalDocs() {
      ExternalDocumentation docs = new ExternalDocumentation();
      docs.setDescription("Documentación externa");
      docs.setUrl("https://example.com/docs");
      return docs;
   }
}
