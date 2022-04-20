package com.bluecreator.springbootreactive;


import com.bluecreator.springbootreactive.domain.Cart;
import com.bluecreator.springbootreactive.domain.CartItem;
import com.bluecreator.springbootreactive.domain.Item;
import com.bluecreator.springbootreactive.repository.CartRepository;
import com.bluecreator.springbootreactive.repository.ItemRepository;
import com.bluecreator.springbootreactive.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


// @ExtendWith(SpringExtension.class)
public class BlockHoundIntegrationTest {

    InventoryService inventoryService;

    //@MockBean
    ItemRepository itemRepository;

    //@MockBean
    CartRepository cartRepository;

    // @BeforeEach
    void setUp(){
        Item sampleItem = new Item("item1", "TV tray", "Alf TV tray", 19.99);
        CartItem sampleCartItem = new CartItem(sampleItem);
        Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));

        when(cartRepository.findById(anyString())).thenReturn(Mono.<Cart> empty().hide());
        when(itemRepository.findById(anyString())).thenReturn(Mono.just(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));


        inventoryService = new InventoryService(itemRepository, cartRepository);
    }

    // @Test
    void threadSleepIsABlockingCall(){
        Mono.delay(Duration.ofSeconds(1))
                .flatMap(tick -> {
                    try {
                        Thread.sleep(10);
                        return Mono.just(true);
                    } catch (InterruptedException e) {
                        // e.printStackTrace();
                        return Mono.error(e);
                    }
                })
                .as(StepVerifier::create)
                .verifyComplete();
    }

    // @Test
    void blockHoundShouldTrapBlockingCall(){
        Mono.delay(Duration.ofSeconds(1))
                .flatMap(tick -> inventoryService.addItemToCart("My Cart", "item1"))
                .as(StepVerifier::create)
                .verifyErrorSatisfies(throwable -> {
                    assertThat(throwable).hasMessageContaining(
                            "block()/blockFirst()/blockLast() are blocking"
                    );
                });
    }

}
