package com.bluecreator.springbootreactive.ch1.controller;

import com.bluecreator.springbootreactive.ch1.domain.Dish;
import com.bluecreator.springbootreactive.ch1.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ServerController {
    private final KitchenService kitchenService;

    @GetMapping(value = "/server", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Dish> serveDishes(){
        return this.kitchenService.getDishes();
    }

    @GetMapping(value = "served-dishes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Dish> deliverDishes(){
        return this.kitchenService.getDishes()
                .map(Dish::deliver);
    }
}
