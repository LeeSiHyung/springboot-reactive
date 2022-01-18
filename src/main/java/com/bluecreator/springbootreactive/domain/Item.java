package com.bluecreator.springbootreactive.domain;

import lombok.*;
import org.springframework.data.annotation.Id;


@Getter
@Setter
public class Item {
    private @Id String id;
    private String name;
    private double price;

    private Item(){};

    public Item(String name, double price){
        this.name = name;
        this.price = price;
    }
}
