package com.bluecreator.springbootreactive.controller;

import com.bluecreator.springbootreactive.repository.CartRepository;
import com.bluecreator.springbootreactive.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    @GetMapping("/")
    private Mono<String> home(){
        return Mono.just("home");
    }

}
