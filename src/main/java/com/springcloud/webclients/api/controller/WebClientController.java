package com.springcloud.webclients.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class WebClientController {

    @GetMapping("/test")
    public Mono<MyUser> doTest(){
        WebClient client = WebClient.create();
        MyUser myUser = new MyUser();
        myUser.setId("my Id");
        myUser.setName("my Name");
        return client.get()
                .uri("http://localhost:8090/webclient/ok")
                .retrieve()
                .bodyToMono(MyUser.class);
    }
}
