package dev.hemraj.api_gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public CustomFilter(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
       return ((exchange, chain) -> {
           String requestHeaderName = config.getRequestHeaderName();
           String responseHeaderName = config.getResponseHeaderName();
           String responseHeaderValue = config.getResponseHeaderValue();

           if(!exchange.getRequest().getHeaders().containsKey(requestHeaderName)){
               exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
               return exchange.getResponse().setComplete();
           }
           return chain.filter(exchange).then(Mono.fromRunnable(()->{
               exchange.getResponse().getHeaders().add(responseHeaderName,responseHeaderValue);
           }));
       });
    }
    public static class Config{
        private String requestHeaderName;
        private String responseHeaderValue;
        private String responseHeaderName;

        public String getRequestHeaderName() {
            return requestHeaderName;
        }

        public void setRequestHeaderName(String requestHeaderName) {
            this.requestHeaderName = requestHeaderName;
        }

        public String getResponseHeaderName() {
            return responseHeaderName;
        }

        public void setResponseHeaderName(String responseHeaderName) {
            this.responseHeaderName = responseHeaderName;
        }

        public String getResponseHeaderValue() {
            return responseHeaderValue;
        }

        public void setResponseHeaderValue(String responseHeaderValue) {
            this.responseHeaderValue = responseHeaderValue;
        }

    }
}

