package com.thoughtworks.CartApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private ItemRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    private Item itemPencil;
    private Item itemEraser;
    private ArrayList<Item> itemList;
    private Cart cart;

    @BeforeEach
    void setUp() {
        itemPencil = new Item("Pencil", 20);
        itemEraser = new Item("Erasure", 5);
        itemList = new ArrayList<>(){
            {
                add(itemPencil);
                add(itemEraser);
            }
        };
        cart = new Cart(itemList);
    }

    @Test
    void shouldCallAddMethodOfCartRepositoryWhenValidItemIsBeingAdded() {
        Item itemScale = new Item("Scale", 15);
        cartService.addItem(itemScale);

        verify(cartRepository, times(1)).add(itemScale);
    }

    @Test
    void shouldNotCallAddMethodOfCartRepositoryWhenDuplicateItemIsBeingAdded() {
        when(cartRepository.contains(itemPencil)).thenReturn(true);

        cartService.addItem(itemPencil);

        verify(cartRepository, never()).add(itemPencil);
    }

    @Test
    void shouldCallRemoveMethodOfCartRepositoryWhenItemIsBeingDeleted() {
        when(cartRepository.contains(itemPencil)).thenReturn(true);

        cartService.deleteItem(itemPencil);

        verify(cartRepository, times(1)).remove(itemPencil);
    }

    @Test
    void shouldNotCallRemoveMethodOfCartRepositoryWhenTheItemIsNotAvailableInTheCart() {
        Item itemScale = new Item("Scale", 15);
        when(cartRepository.contains(itemScale)).thenReturn(false);

        cartService.deleteItem(itemScale);

        verify(cartRepository, never()).remove(itemScale);
    }

    @Test
    void shouldReturnAllCartItemsWhenAllItemsNeedToBeViewed() {
        when(cartRepository.getCart()).thenReturn(cart);

        ArrayList<Item> actualItemList = cartService.viewItems().getItems();

        assertThat(actualItemList, is(equalTo(itemList)));
    }

    @Test
    void shouldReturnTwentyFiveAsTotalCostWhenCartHasPencilWorthTwentyRupeesAndErasureWorthFiveRupees() {
        when(cartRepository.getCart()).thenReturn(cart);

        assertThat(cartService.viewItems().getTotalCost(), is(equalTo(25.0)));
    }

}
