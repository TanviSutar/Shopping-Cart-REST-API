package com.thoughtworks.CartApp;

import com.thoughtworks.CartApp.custom_exceptions.ItemAlreadyExistsException;
import com.thoughtworks.CartApp.custom_exceptions.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private ItemRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    private Item itemPencil;
    private Item itemEraser;
    private ItemDTO itemDTOSharpener;
    private ArrayList<Item> itemList;
    private CartDTO cart;

    @BeforeEach
    void setUp() {
        itemPencil = new Item("Pencil", 20);
        itemEraser = new Item("Erasure", 5);
        itemDTOSharpener = new ItemDTO("Sharpener", 30);
        itemList = new ArrayList<>() {
            {
                add(itemPencil);
                add(itemEraser);
            }
        };
        cart = new CartDTO(itemList);
    }

    @Test
    void shouldCallAddMethodOfCartRepositoryWhenValidItemIsBeingAdded() throws ItemAlreadyExistsException {
        cartService.addItem(itemDTOSharpener);

        verify(cartRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowItemAlreadyExistsExceptionWhenDuplicateItemIsBeingAdded() {
        when(cartRepository.existsByName(itemDTOSharpener.getName())).thenReturn(true);

        assertThrows(ItemAlreadyExistsException.class, ()-> {
            cartService.addItem(itemDTOSharpener);
        });

        verify(cartRepository, never()).save(any());
    }

    @Test
    void shouldCallRemoveMethodOfCartRepositoryWhenItemIsBeingDeleted() {
        when(cartRepository.existsById(any())).thenReturn(true);

        cartService.deleteItem(itemPencil.getId());

        verify(cartRepository, times(1)).deleteById(any());
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenTheItemIsNotAvailableInTheCart() {
        Item itemScale = new Item("Scale", 15);
        when(cartRepository.existsById(any())).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> {
            cartService.deleteItem(itemScale.getId());
        });

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

    @Test
    void shouldReturnItemGivenTheItemId() {
        when(cartRepository.existsById(itemPencil.getId())).thenReturn(true);
        when(cartRepository.findById(itemPencil.getId())).thenReturn(Optional.ofNullable(itemPencil));

        assertThat(cartService.getItemById(itemPencil.getId()), is(equalTo(itemPencil)));
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenNoItemByGivenIdIsFound() {
        when(cartRepository.existsById(itemPencil.getId())).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> {
            cartService.getItemById(itemPencil.getId());
        });

        verify(cartRepository, never()).findById(itemPencil.getId());
    }
}
