package com.thoughtworks.CartApp;

import com.thoughtworks.CartApp.custom_exceptions.ItemAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private ItemRepository itemRepository;

    int addItem(ItemDTO itemDTO) throws ItemAlreadyExistsException {
        if(itemRepository.existsByName(itemDTO.getName())){
            throw new ItemAlreadyExistsException();
        }
        Item item = new Item(itemDTO.getName(), itemDTO.getCost());
        itemRepository.save(item);
        return item.getId();
    }

    void deleteItem(int itemId) {
        if (!itemRepository.existsById(itemId)) {
            return;
        }
        itemRepository.deleteById(itemId);
    }

    CartDTO viewItems() {
        return new CartDTO(itemRepository.findAll());
    }

    public Item getItemById(int id) {
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()){
            return item.get();
        }
        return null;
    }
}
