package com.springcloud.webclients.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class WebClientController {

    @GetMapping("/test")
    public Mono<TestUser> doTest(){
        WebClient client = WebClient.create();
        TestUser testUser = new TestUser();
        testUser.setId("my Id");
        testUser.setName("my Name");
        return client.get()
                .uri("http://localhost:8090/webclient/ok")
                .retrieve()
                .bodyToMono(TestUser.class);
    }
}
