package com.alcosystem.alcosystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Drink {

    private Long id;
    private String drinkName;
    private Integer degree;

}
