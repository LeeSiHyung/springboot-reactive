package com.bluecreator.springbootreactive.controller;

import com.bluecreator.springbootreactive.domain.Cart;
import com.bluecreator.springbootreactive.repository.CartRepository;
import com.bluecreator.springbootreactive.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class HomeControllerV2 {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    // @GetMapping
    // private Mono<Rendering> home(){
    //     return Mono.just(Rendering.view("home2")
    //             .modelAttribute("items", this.itemRepository.findAll())
    //             .modelAttribute("cart", this.cartRepository.findById("My Cart").defaultIfEmpty(new Cart("My Cart")))
    //             .build()
    //     );
    // }

    @GetMapping
    private Mono<String> home(Model model){
        model.addAttribute("items", this.itemRepository.findAll());
        model.addAttribute("cart", this.cartRepository.findById("My Cart").defaultIfEmpty(new Cart("My Cart")));
        return Mono.just("home2");
    }


}
