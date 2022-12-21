package com.example.productservice.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class ItemQuantityDTO {

    private HashMap<Integer, Integer> itemsFromShoppingCart;
}
