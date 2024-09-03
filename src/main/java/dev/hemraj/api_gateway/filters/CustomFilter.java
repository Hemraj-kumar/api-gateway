package dev.hemraj.api_gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public CustomFilter(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
       return ((exchange, chain) -> {
           ServerHttpRequest request = exchange.getRequest();
           HttpHeaders headers = request.getHeaders();

           if(!headers.containsKey(config.getRequestHeaderName())){
               exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
               return exchange.getResponse().setComplete();
           }
           ServerHttpRequest modifiedRequest = request.mutate()
                   .header(config.getRequestHeaderName(),headers.getFirst(config.getRequestHeaderName())).build();

           return chain.filter(exchange.mutate().request(modifiedRequest).build());
       });
    }
    public static class Config{
        private String requestHeaderName;
        private String requestHeaderValue;

        public String getRequestHeaderName() {
            return requestHeaderName;
        }

        public void setRequestHeaderName(String requestHeaderName) {
            this.requestHeaderName = requestHeaderName;
        }

        public void setRequestHeaderValue(String requestHeaderValue){
            this.requestHeaderValue = requestHeaderValue;
        }
        public String getRequestHeaderValue(){
            return requestHeaderValue;
        }

    }
}

