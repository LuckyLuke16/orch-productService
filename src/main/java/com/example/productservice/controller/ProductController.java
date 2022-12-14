package com.example.productservice.controller;

import com.example.productservice.model.ItemDTO;
import com.example.productservice.model.entity.Item;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ProductController implements ProductOperations {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public List<ItemDTO> fetchItemsByGenre(String genre) {
        List<ItemDTO> allItemsList;
        try {
            if(genre.equals("ALL"))
                allItemsList = productService.fetchAllProducts();
            else
                allItemsList = productService.fetchProductsBySpecificGenre(genre);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return allItemsList;
    }

    public Item fetchSingleItem(int itemID) {
        Item singleItemToFetch;
        try {
            singleItemToFetch = productService.fetchSingleProduct(itemID);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return singleItemToFetch;
    }
}