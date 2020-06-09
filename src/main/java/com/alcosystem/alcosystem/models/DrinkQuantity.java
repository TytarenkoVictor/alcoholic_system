package com.alcosystem.alcosystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrinkQuantity {

    private Long id;
    private String drinkName;
    private Long quantity;

}
