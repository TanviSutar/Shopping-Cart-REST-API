package com.thoughtworks.CartApp;

import com.thoughtworks.CartApp.custom_exceptions.ItemAlreadyExistsException;
import com.thoughtworks.CartApp.custom_exceptions.ItemNotFoundException;
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
        Item item = new Item(itemDTO.getName().toLowerCase(), itemDTO.getCost());
        itemRepository.save(item);
        return item.getId();
    }

    void deleteItemById(int itemId) {
        if (!itemRepository.existsById(itemId)) {
           throw new ItemNotFoundException();
        }
        itemRepository.deleteById(itemId);
    }

    CartDTO viewItems() {
        return new CartDTO(itemRepository.findAll());
    }

    public Item getItemById(int id) {
        if (!itemRepository.existsById(id)) {
            throw new ItemNotFoundException();
        }
        return itemRepository.findById(id).get();
    }

    public CartDTO searchByStringPattern(String searchString) {
        List<Item> itemList = itemRepository.findByNameLike("%"+searchString+"%");
        if(itemList.size() == 0){
            throw new ItemNotFoundException();
        }
        return new CartDTO(itemList);
    }

    public void deleteItemByName(String name) {
        if(itemRepository.findByNameLike("%"+name+"%").size() == 0){
            throw new ItemNotFoundException();
        }
        itemRepository.deleteByName(name);
    }
}
