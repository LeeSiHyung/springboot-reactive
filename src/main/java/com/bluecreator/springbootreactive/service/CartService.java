package com.bluecreator.springbootreactive.service;

// @Service
// @RequiredArgsConstructor
public class CartService {
    /*
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public Mono<Cart> addToCart(String cartId,  String id){
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
}
