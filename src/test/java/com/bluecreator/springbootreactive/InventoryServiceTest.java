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

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


//@ExtendWith(SpringExtension.class)
public class InventoryServiceTest {

    //@MockBean
    private ItemRepository itemRepository;

    //@MockBean
    private CartRepository cartRepository;

    private InventoryService inventoryService;

    private Item sampleItem;

    //@BeforeEach
    public void setUp(){
        sampleItem = new Item("item1", "TV tray", "Alf TV tray", 19.99);
        CartItem sampleCartItem = new CartItem(sampleItem);
        Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));

        when(cartRepository.findById(anyString())).thenReturn(Mono.empty());
        when(itemRepository.findById(anyString())).thenReturn(Mono.just(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));

        inventoryService = new InventoryService(itemRepository, cartRepository);

    }

    //@Test
    public void addItemToEmptyCartShouldProduceOneCartItem(){
        inventoryService.addItemToCart("My Cart", "item1")
                // as Mono의 대상유형으로 변경
                // StepVerifier
                // .as(StepVerifier::create)
                .as(cartMono -> {
                    StepVerifier.FirstStep<Cart> stepVerifier = StepVerifier.create(cartMono);
                    return stepVerifier;
                })
                // 함수와 람다식을 이용해서 결과를 검증한다
                .expectNextMatches(cart -> {

                    // 테스트를 위해 리스트의 객체들의 이름을 검증한다고 해보면 반복문에서
                    // 이름을 꺼내와 또 다른 리스트에 담고 비교하는 불편한 과정을 수반한다.
                    // 하지만 extracting()을 사용하면 이것을 아주 간편하게 해결할 수 있다

                    // assertThat(list).extracting("name", "age") .contains(tuple("Kim", 22), tuple("Park", 25), tuple("Lee", 25), tuple("Amy", 22), tuple("Jack",22));
                    //출처: https://pjh3749.tistory.com/241 [JayTech의 기술 블로그]

                    //// 포함되어 있니
                    //assertThat(list).contains("1", "2");
                    //// 중복된 값도 반영됨
                    //assertThat(list).containsOnly("2","1","3");
                    //// 순서 까지 정확해야함
                    //assertThat(list).containsExactly("1", "1", "2", "3");
                    //// 순서 정확하지 않아도됨
                    //assertThat(list).containsExactlyInAnyOrder("2", "3", "1", "1");
                    assertThat(cart.getCartItems()).extracting(CartItem::getQuantity)
                            .containsExactlyInAnyOrder(1);

                    assertThat(cart.getCartItems()).extracting(CartItem::getItem)
                            // .containsExactly(new Item("item1", "TV tray", "Alf TV tray", 19.99));
                            .containsExactly(sampleItem);

                    return true;

                }).verifyComplete();
    }

    // @Test
    public void alternativeWayToTest(){
        // StepVerifier가 구독을 한다. 블로킹 방식으로 기다림
        StepVerifier.create(
                inventoryService.addItemToCart("My Cart", "item1")
        // expectNextMatches는 next를 수행하면서 값 검증을 한다.
        ).expectNextMatches(cart -> {
            assertThat(cart.getCartItems()).extracting(CartItem::getQuantity)
                    .containsExactlyInAnyOrder(1);

            assertThat(cart.getCartItems()).extracting(CartItem::getItem)
                    // .containsExactly(new Item("item1", "TV tray", "Alf TV tray", 19.99));
                    .containsExactly(sampleItem);
            return true;
        // verifyComplete 는 onComplete 시그널을 확인
        }).verifyComplete();
    }

}
