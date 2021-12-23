package com.thoughtworks.CartApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

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

    @BeforeEach
    void setUp(){
        itemPencil = new Item("Pencil", 20);
    }

    @Test
    void shouldCallAddMethodOfCartRepositoryWhenValidItemIsBeingAdded() {
        Item itemEraser = new Item("Eraser", 20);
        cartService.addItem(itemEraser);

        verify(cartRepository, times(1)).add(itemEraser);
    }

    @Test
    void shouldNotCallAddMethodOfCartRepositoryWhenDuplicateItemIsBeingAdded() {
        when(cartRepository.contains(itemPencil)).thenReturn(true);

        cartService.addItem(itemPencil);

        verify(cartRepository, never()).add(itemPencil);
    }

    @Test
    void shouldCallRemoveMethodOfCartRepositoryWhenItemIsBeingDeleted(){
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
        ArrayList<Item> expectedItemList = new ArrayList<>(){
            {
                add(itemPencil);
            }
        };
        when(cartRepository.cartItems()).thenReturn(expectedItemList);

        ArrayList<Item> actualItemList = cartService.viewItems();

        assertThat(actualItemList, is(equalTo(expectedItemList)));
    }

    @Test
    void shouldReturnTwentyFiveAsTotalCostWhenCartHasPencilWorthTwentyRupeesAndErasureWorthFiveRupees() {
        when(cartRepository.totalCost()).thenReturn(25.0);

        assertThat(cartService.totalCost(), is(equalTo(25.0)));
    }

}
