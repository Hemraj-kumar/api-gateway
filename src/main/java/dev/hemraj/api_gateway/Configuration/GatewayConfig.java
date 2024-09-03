package dev.hemraj.api_gateway.Configuration;

import dev.hemraj.api_gateway.filters.CustomFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, CustomFilter filter){
        CustomFilter.Config filterConfig = new CustomFilter.Config();
        filterConfig.setRequestHeaderName("X-Request-Example");

        return builder.routes()
                .route("example_route", r -> r.path("/api/public/**")
                        .filters(f -> f.filter(filter.apply(filterConfig)))
                        .uri("http://localhost:4000"))
                .build();
    }
}
