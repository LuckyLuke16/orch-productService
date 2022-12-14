package com.example.productservice.service;

import com.example.productservice.exception.NoItemsFoundException;
import com.example.productservice.model.ItemDTO;
import com.example.productservice.model.entity.Item;
import com.example.productservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    ModelMapper modelMapper;

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public List<ItemDTO> fetchProductsBySpecificGenre(String genre) {
        List<ItemDTO> allItemDTOs = new ArrayList<>();
        try {
            List<Item> itemsFromDB = productRepository.findByGenre(genre);
            if(!itemsFromDB.isEmpty())
                allItemDTOs = itemToItemDTOList(itemsFromDB);
        } catch (Exception e) {
            throw new NoItemsFoundException();
        }
        return allItemDTOs;
    }

    public List<ItemDTO> fetchAllProducts() {
        List<ItemDTO> allItemDTOs = new ArrayList<>();
        try {
            List<Item> itemsFromDB = productRepository.findAll();
            if(!itemsFromDB.isEmpty())
                allItemDTOs = itemToItemDTOList(itemsFromDB);
        } catch (Exception e) {
            throw new NoItemsFoundException();
        }
        return allItemDTOs;
    }

    private List<ItemDTO> itemToItemDTOList(List<Item> itemsFromDB) {
        List<ItemDTO> itemDTOs = new ArrayList<>();
         for(Item i : itemsFromDB) {
             try {
                 itemDTOs.add(modelMapper.map(i, ItemDTO.class));
             } catch (Exception e) {
                 logger.warn("Item with id {} could not be converted to an ItemDTO", i.getId());
             }
         }
         return itemDTOs;
    }

    public Item fetchSingleProduct(int itemID) {
        try {
            Optional<Item> singleItemFromDB = productRepository.findById(itemID);
            if(singleItemFromDB.isPresent())
                return singleItemFromDB.get();
             else
                logger.warn("Item with id {} was not found", itemID);
        } catch (Exception e) {
            logger.warn("Item with id {} was not found: ", itemID, e);
        }
        throw new NoItemsFoundException();
    }
}
