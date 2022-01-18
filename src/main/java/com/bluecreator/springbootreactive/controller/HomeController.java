package com.bluecreator.springbootreactive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    @GetMapping
    private Mono<String> home(){
        return Mono.just("home");
    }

}
