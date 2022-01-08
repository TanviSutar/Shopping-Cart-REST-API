package com.practice.CartApp;

import com.practice.CartApp.custom_exceptions.ItemAlreadyExistsException;
import com.practice.CartApp.custom_exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private ItemRepository itemRepository;

    int addItem(ItemDTO itemDTO){
        if(itemRepository.existsByName(itemDTO.getName())){
            throw new ItemAlreadyExistsException();
        }
        Item savedItem = itemRepository.save(new Item(itemDTO.getName().toLowerCase(), itemDTO.getCost()));
        return savedItem.getId();
    }

    public Item getItemById(int id) {
        if (!itemRepository.existsById(id)) {
            throw new ItemNotFoundException();
        }
        return itemRepository.findById(id).get();
    }

    public CartDTO getItemListByNameBasedPattern(String searchString) {
        List<Item> itemList = itemRepository.findByNameContaining(searchString);
        if(itemList.size() == 0){
            throw new ItemNotFoundException();
        }
        return new CartDTO(itemList);
    }

    CartDTO getItemList() {
        return new CartDTO(itemRepository.findAll());
    }

    void deleteItemById(int itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new ItemNotFoundException();
        }
        itemRepository.deleteById(itemId);
    }

    public void deleteItemByName(String name) {
        if(itemRepository.findByNameContaining(name).size() == 0){
            throw new ItemNotFoundException();
        }
        itemRepository.deleteByName(name);
    }
}
