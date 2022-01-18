package com.bluecreator.springbootreactive.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Dish {
    private final String description;
    private boolean delivered = false;
    public static Dish deliver(Dish dish){
        Dish deliverdDish = new Dish(dish.description);
        deliverdDish.delivered = true;
        return deliverdDish;
    }
}
