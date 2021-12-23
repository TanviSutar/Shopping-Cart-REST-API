package com.thoughtworks.CartApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class ItemRepositoryTest {

    private ItemRepository repository;
    private Item itemPencil;
    private Item itemErasure;

    @BeforeEach
    void setUp() {
        repository = new ItemRepository();
        itemPencil = new Item("Pencil", 20);
        itemErasure = new Item("Erasure", 5);
        repository.add(itemPencil);
        repository.add(itemErasure);
    }

    @Test
    void shouldAddItemToCart() {
        Item itemSharpener = new Item("Sharpener", 10);
        repository.add(itemSharpener);

        assertThat(repository.contains(itemSharpener), is(equalTo(Boolean.TRUE)));
    }

    @Test
    void shouldDeleteItemFromTheCart() {
        repository.remove(itemPencil);

        assertThat(repository.contains(itemPencil), is(equalTo(Boolean.FALSE)));
    }

    @Test
    void shouldAllowViewingItemsInTheCart() {
        ArrayList<Item> expectedItems = new ArrayList<>() {
            {
                add(itemPencil);
                add(itemErasure);
            }
        };
        ArrayList<Item> actualItems = repository.cartItems();

        assertThat(expectedItems, is(equalTo(actualItems)));
    }

    @Test
    void shouldReturnTwentyFiveAsTotalCostOfPencilWorthRupeesTwentyAndErasureWorthRupeesFive() {
        assertThat(repository.totalCost(), is(closeTo(25, 0.001)));
    }
}
