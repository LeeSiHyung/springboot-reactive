package com.bluecreator.springbootreactive.service;

import com.bluecreator.springbootreactive.domain.Cart;
import com.bluecreator.springbootreactive.domain.CartItem;
import com.bluecreator.springbootreactive.repository.CartRepository;
import com.bluecreator.springbootreactive.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public Mono<Cart> addToCart(String cartId,  String id){
        return this.cartRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId))
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem().getId().equals(id))
                        .findAny()
                        .map(cartItem -> {
                            // 이미 장바구니에 존재할 경우 수량만 추가
                            cartItem.increment();
                            return Mono.just(cart);
                        })
                        .orElseGet(() -> {
                            // 장바구니에 없으면 item을 장바구니에 추가한다.
                            return this.itemRepository.findById(id)
                                    .map(CartItem::new)
                                    .map(cartItem -> {
                                        cart.getCartItems().add(cartItem);
                                        return cart;
                                    });
                        }))
                .flatMap(this.cartRepository::save);
    }

}
