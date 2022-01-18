package com.bluecreator.springbootreactive.ch1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    @GetMapping("/ch1")
    private Mono<String> home(){
        return Mono.just("ch1/home");
    }

}
