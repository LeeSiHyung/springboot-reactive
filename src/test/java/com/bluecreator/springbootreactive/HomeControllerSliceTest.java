package com.bluecreator.springbootreactive;

import com.bluecreator.springbootreactive.domain.Cart;
import com.bluecreator.springbootreactive.domain.Item;
import com.bluecreator.springbootreactive.service.InventoryService;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


// @WebFluxTest(HomeControllerV2.class)
public class HomeControllerSliceTest {

    //@Autowired
    private WebTestClient client;

    //@MockBean
    InventoryService inventoryService;

    // @MockBean
    // ItemRepository itemRepository;

    // @MockBean
    // CartRepository cartRepository;


    //@Test
    void homePage(){
        when(inventoryService.getInventory()).thenReturn(Flux.just(
                new Item("id1", "name1", "disc1", 1.99),
                new Item("id2", "name2", "disc2", 9.99)
        ));

        when(inventoryService.getCart("My Cart"))
                .thenReturn(Mono.just(new Cart("My Cart")));

        client.get().uri("/").exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(exchangeResult -> {
                    assertThat(exchangeResult.getResponseBody()).contains("action=\"/add/id1\"");
                    assertThat(exchangeResult.getResponseBody()).contains("action=\"/add/id2\"");
                });
    }

}
