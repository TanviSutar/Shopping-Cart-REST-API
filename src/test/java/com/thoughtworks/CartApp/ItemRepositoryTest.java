package com.thoughtworks.CartApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
@Disabled
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private Item itemPencil, itemErasure;

    @BeforeEach
    void setUp() {
        repository.save(itemPencil);
        repository.save(itemErasure);
    }

    @Test
    void shouldAddItemToCart() {
        Item itemSharpener = new Item("Sharpener", 10);
        repository.save(itemSharpener);

        assertThat(repository.existsById(itemSharpener.getId()), is(equalTo(Boolean.TRUE)));
    }

    @Test
    void shouldDeleteItemFromTheCart() {
        repository.deleteById(itemPencil.getId());

        assertThat(repository.existsById(itemPencil.getId()), is(equalTo(Boolean.FALSE)));
    }

    @Test
    void shouldAllowViewingItemsInTheCart() {
        ArrayList<Item> expectedItems = new ArrayList<>() {
            {
                add(itemPencil);
                add(itemErasure);
            }
        };
        ArrayList<Item> actualItems = (ArrayList<Item>) repository.findAll();

        assertThat(expectedItems, is(equalTo(actualItems)));
    }
}
