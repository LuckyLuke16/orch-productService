package com.example.productservice.controller;

import com.example.productservice.model.ItemDTO;
import com.example.productservice.model.entity.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/items")
public interface ProductOperations {

    @GetMapping
    List<ItemDTO> fetchItemsByGenre(@RequestParam String genre);

    @GetMapping("/{itemID}")
    Item fetchSingleItem(@PathVariable int itemID);

}
