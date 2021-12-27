package com.thoughtworks.CartApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

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
    private Item itemEraser;
    private ArrayList<Item> itemList;
    private Cart cart;

    @BeforeEach
    void setUp() {
        itemPencil = new Item("Pencil", 20);
        itemEraser = new Item("Erasure", 5);
        itemList = new ArrayList<>() {
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

        verify(cartRepository, times(1)).save(any());
    }

    @Test
    void shouldNotCallAddMethodOfCartRepositoryWhenDuplicateItemIsBeingAdded() {
        when(cartRepository.existsByName(any())).thenReturn(true);

        cartService.addItem(itemPencil);

        verify(cartRepository, never()).save(any());
    }

    @Test
    void shouldCallRemoveMethodOfCartRepositoryWhenItemIsBeingDeleted() {
        when(cartRepository.existsById(any())).thenReturn(true);

        cartService.deleteItem(itemPencil.getId());

        verify(cartRepository, times(1)).deleteById(any());
    }

    @Test
    void shouldNotCallRemoveMethodOfCartRepositoryWhenTheItemIsNotAvailableInTheCart() {
        Item itemScale = new Item("Scale", 15);
        when(cartRepository.existsById(any())).thenReturn(false);

        cartService.deleteItem(itemScale.getId());

        verify(cartRepository, never()).deleteById(itemScale.getId());
    }

    @Test
    void shouldReturnAllCartItemsWhenAllItemsNeedToBeViewed() {
        when(cartRepository.findAll()).thenReturn(itemList);

        Iterable<Item> actualItemList = cartService.viewItems().getItems();

        assertThat(actualItemList, is(equalTo(itemList)));
    }

    @Test
    void shouldReturnTwentyFiveAsTotalCostWhenCartHasPencilWorthTwentyRupeesAndErasureWorthFiveRupees() {
        when(cartRepository.findAll()).thenReturn(itemList);

        assertThat(cartService.viewItems().getTotalCost(), is(equalTo(25.0)));
    }

}
