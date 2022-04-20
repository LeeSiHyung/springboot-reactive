package com.bluecreator.springbootreactive.service;

import com.bluecreator.springbootreactive.domain.Cart;
import com.bluecreator.springbootreactive.domain.CartItem;
import com.bluecreator.springbootreactive.domain.Item;
import com.bluecreator.springbootreactive.repository.CartRepository;
import com.bluecreator.springbootreactive.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    // private final ReactiveFluentMongoOperations reactiveFluentMongoOperations;

    public Mono<Cart> getCart(String cartId) {
        return this.cartRepository.findById(cartId);
    }

    public Flux<Item> getInventory() {
        return this.itemRepository.findAll();
    }

    Mono<Item> saveItem(Item newItem) {
        return this.itemRepository.save(newItem);
    }

    Mono<Void> deleteItem(String id) {
        return this.itemRepository.deleteById(id);
    }

    public Flux<Item> searchByExample(String name, String description, boolean useAnd){
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (useAnd
                ? ExampleMatcher.matchingAll()
                : ExampleMatcher.matchingAny()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnorePaths("price")
        );

        Example<Item> probe = Example.of(item, matcher);
        return itemRepository.findAll(probe);
    }

/*
    public Mono<Cart> addItemToCart(String cartId, String id){
        return this.cartRepository.findById(cartId)
                .log("foundCart")
                .defaultIfEmpty(new Cart(cartId))
                .log("emptyCart")
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem().getId().equals(id))
                        .findAny()
                        .map(cartItem -> {
                            // 이미 장바구니에 존재할 경우 수량만 추가
                            cartItem.increment();
                            return Mono.just(cart).log("newCartItem");
                        })
                        .orElseGet(() -> {
                            // 장바구니에 없으면 item을 장바구니에 추가한다.
                            return this.itemRepository.findById(id)
                                    .log("fetchedItem")
                                    .map(CartItem::new)
                                    .log("cartItem")
                                    .map(cartItem -> {
                                        cart.getCartItems().add(cartItem);
                                        return cart;
                                    }).log("addedCartItem");
                        }))
                .log("cartWithAnnotherItem")
                .flatMap(this.cartRepository::save)
                .log("savedCart");
    }

*/

    public Mono<Cart> addItemToCart(String cartId, String id){

        Cart myCart = this.cartRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId)).block();

        return myCart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem().getId().equals(id))
                        .findAny()
                        .map(cartItem -> {
                            // 이미 장바구니에 존재할 경우 수량만 추가
                            cartItem.increment();
                            return Mono.just(myCart);
                        })
                        .orElseGet(() -> {
                            // 장바구니에 없으면 item을 장바구니에 추가한다.
                            return this.itemRepository.findById(id)
                                    .map(CartItem::new)
                                    .map(cartItem -> {
                                        myCart.getCartItems().add(cartItem);
                                        return myCart;
                                    });
                        })
                .flatMap(this.cartRepository::save);
    }

//    public Flux<Item> searchByFluentExample(String name, String descrption, boolean useAnd){
//        Item item = new Item(name, descrption, 0.0);
//
//        ExampleMatcher matcher = (useAnd
//                ? ExampleMatcher.matchingAll()
//                : ExampleMatcher.matchingAny())
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
//                .withIgnoreCase()
//                .withIgnorePaths("price");
//
//        return reactiveFluentMongoOperations.query(Item.class)
//                .matching(query(byExample(Example.of(item, matcher))))
//                .all();
//    }

}
