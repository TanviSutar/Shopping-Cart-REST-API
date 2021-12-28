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
    private ItemRepository itemRepository;

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

        verify(itemRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowItemAlreadyExistsExceptionWhenDuplicateItemIsBeingAdded() {
        when(itemRepository.existsByName(itemDTOSharpener.getName())).thenReturn(true);

        assertThrows(ItemAlreadyExistsException.class, ()-> {
            cartService.addItem(itemDTOSharpener);
        });

        verify(itemRepository, never()).save(any());
    }

    @Test
    void shouldCallRemoveMethodOfCartRepositoryWhenItemIsBeingDeleted() {
        when(itemRepository.existsById(any())).thenReturn(true);

        cartService.deleteItem(itemPencil.getId());

        verify(itemRepository, times(1)).deleteById(any());
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenTheItemIsNotAvailableInTheCart() {
        Item itemScale = new Item("Scale", 15);
        when(itemRepository.existsById(any())).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> {
            cartService.deleteItem(itemScale.getId());
        });

        verify(itemRepository, never()).deleteById(itemScale.getId());
    }

    @Test
    void shouldReturnAllCartItemsWhenAllItemsNeedToBeViewed() {
        when(itemRepository.findAll()).thenReturn(itemList);

        Iterable<Item> actualItemList = cartService.viewItems().getItems();

        assertThat(actualItemList, is(equalTo(itemList)));
    }

    @Test
    void shouldReturnTwentyFiveAsTotalCostWhenCartHasPencilWorthTwentyRupeesAndErasureWorthFiveRupees() {
        when(itemRepository.findAll()).thenReturn(itemList);

        assertThat(cartService.viewItems().getTotalCost(), is(equalTo(25.0)));
    }

    @Test
    void shouldReturnItemGivenTheItemId() {
        when(itemRepository.existsById(itemPencil.getId())).thenReturn(true);
        when(itemRepository.findById(itemPencil.getId())).thenReturn(Optional.ofNullable(itemPencil));

        assertThat(cartService.getItemById(itemPencil.getId()), is(equalTo(itemPencil)));
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenNoItemByGivenIdIsFound() {
        when(itemRepository.existsById(itemPencil.getId())).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> {
            cartService.getItemById(itemPencil.getId());
        });

        verify(itemRepository, never()).findById(itemPencil.getId());
    }

    //TODO review
    @Test
    void shouldReturnItemListHavingAllItemsWithNameMatchingTheGivenSearchString() {
        ArrayList<Item> itemList = new ArrayList<>(){
            {
                add(itemPencil);
            }
        };
        String searchString = "pen";

        when(itemRepository.findByNameLike(any())).thenReturn(itemList);

        CartDTO cartDTO = cartService.searchByStringPattern(searchString);

        verify(itemRepository).findByNameLike(any());

    }
}
