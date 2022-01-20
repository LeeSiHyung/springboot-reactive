package com.bluecreator.springbootreactive.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;


@Getter
@Setter
public class Cart {
    private @Id String id;
    private List<CartItem> cartItems;
    private Cart(){};
    public Cart(String id){
        this.id = id;
    }
    public Cart(List<CartItem> cartItems){
        this.cartItems = cartItems;
    }
}
