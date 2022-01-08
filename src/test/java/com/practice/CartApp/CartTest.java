package com.practice.CartApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CartTest {

    private CartDTO cart;
    private ArrayList<Item> items;
    private double totalCost;

    @BeforeEach
    void setUp() {
        items = new ArrayList<>() {
            {
                add(new Item("Apple", 50.0));
                add(new Item("Orange", 90.50));
            }
        };
        totalCost = 140.50;
        cart = new CartDTO(items);
    }

    @Test
    void shouldReturnItemsList() {
        assertThat(cart.getItems(), is(equalTo(items)));
    }

    @Test
    void shouldReturnOneFortyPointFiveAsTotalCost() {
        assertThat(cart.getTotalCost(), is(equalTo(totalCost)));
    }
}
