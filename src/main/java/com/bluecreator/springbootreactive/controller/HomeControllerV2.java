package com.bluecreator.springbootreactive.controller;

import com.bluecreator.springbootreactive.domain.Cart;
import com.bluecreator.springbootreactive.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class HomeControllerV2 {

    // private final CartService cartService;
    // private final ItemRepository itemRepository;
    // private final CartRepository cartRepository;
    private final InventoryService inventoryService;

    @GetMapping
    private Mono<Rendering> home(){
        return Mono.just(Rendering.view("home2")
                //.modelAttribute("items", this.itemRepository.findAll())
                // .modelAttribute("items", this.itemRepository.findAll().doOnNext(System.out::println))
                //.modelAttribute("cart", this.cartRepository.findById("My Cart").defaultIfEmpty(new Cart("My Cart")))
                .modelAttribute("items", this.inventoryService.getInventory())
                .modelAttribute("cart", this.inventoryService.getCart("My Cart").defaultIfEmpty(new Cart("My Cart")))
                .build()
        );
    }

    @PostMapping("/add/{id}")
    Mono<String> addToCart(@PathVariable String id){
        // return this.cartService.addToCart("My Cart", id).thenReturn("redirect:/");

        return this.inventoryService.addItemToCart("My Cart", id)
                .thenReturn("redirect:/");
    }

    @GetMapping("/search")
    Mono<Rendering> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam boolean useAnd){

        return Mono.just(Rendering.view("home2")
                .modelAttribute("items",
                        inventoryService.searchByExample(name, description, useAnd))
                // .modelAttribute("cart", this.cartRepository.findById("My Cart")
                .modelAttribute("cart", this.inventoryService.getCart("My Cart")
                        .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }

}
