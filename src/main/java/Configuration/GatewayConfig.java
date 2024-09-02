package Configuration;

import filters.CustomFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, CustomFilter filter){
        return builder.routes()
                .route("example_route", r -> r.path("/api/public/**")
                        .filters(f -> f.filter(filter.apply(new CustomFilter.Config())))
                        .uri("http://localhost:4000"))
                .build();
    }
}
