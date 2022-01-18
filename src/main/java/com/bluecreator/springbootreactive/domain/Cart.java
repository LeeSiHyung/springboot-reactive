package com.bluecreator.springbootreactive.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Cart {
    private @Id String id;
    private List<CartItem> cartItems;
    private Cart(){};
}
