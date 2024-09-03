package dev.hemraj.reactive_microservice.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
@RequestMapping("/api/public")
@RestController
@Slf4j
public class GeneralController {
    @GetMapping("/hello")
    public Mono<String> getClientMessage(ServerHttpRequest request){
        HttpHeaders headers = request.getHeaders();
        log.info("headers are being checked!");
        String requestHeader= headers.getFirst("X-Request-Example");
        if(requestHeader!=null) {
            log.info("response is issued!");
            return Mono.just("Hello from microservice "+requestHeader);
        }else{
            log.info("response is not issued!");
            return Mono.just("Invalid credentials!");
        }
    }
}
