
package com.ecommerce.cloudgateway.config;

import java.util.Objects;

import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public CommandLineRunner openApiGroups(RouteDefinitionLocator locator,
			SwaggerUiConfigParameters swaggerUiParameters) {
		return args -> Objects.requireNonNull(locator.getRouteDefinitions().collectList().block()).stream()
				.map(RouteDefinition::getId).filter(id -> id.matches(".*-pedidoapi"))
				.map(id -> id.replace("-pedidoapi", "")).forEach(swaggerUiParameters::addGroup);
	}
}
