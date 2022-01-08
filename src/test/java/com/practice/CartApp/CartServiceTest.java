package com.practice.CartApp;

import com.practice.CartApp.custom_exceptions.ItemAlreadyExistsException;
import com.practice.CartApp.custom_exceptions.ItemNotFoundException;
import org.junit.jupiter.api.BeforeAll;
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

    private static Item itemPencil;
    private static ItemDTO itemDTOSharpener;
    private static ArrayList<Item> itemList;
    private static CartDTO cartDTO;

    @BeforeAll
    static void setUp() {
        itemPencil = new Item("Pencil", 20);
        itemDTOSharpener = new ItemDTO("Sharpener", 30);
        itemList = new ArrayList<>() {
            {
                add(itemPencil);
            }
        };
        cartDTO = new CartDTO(itemList);
    }

    @Test
    void shouldCallAddMethodOfCartRepositoryWhenValidItemIsBeingAdded() {
        Item itemSharpener = new Item(itemDTOSharpener.getName(), itemDTOSharpener.getCost());
        when(itemRepository.save(any())).thenReturn(itemSharpener);

        int savedId = cartService.addItem(itemDTOSharpener);

        assertThat(savedId, is(equalTo(itemSharpener.getId())));
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
    void shouldReturnItemGivenTheItemId() {
        when(itemRepository.existsById(itemPencil.getId())).thenReturn(true);
        when(itemRepository.findById(itemPencil.getId())).thenReturn(Optional.ofNullable(itemPencil));

        assertThat(cartService.getItemById(itemPencil.getId()), is(equalTo(itemPencil)));
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenNoItemWithGivenIdIsFound() {
        when(itemRepository.existsById(itemPencil.getId())).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> {
            cartService.getItemById(itemPencil.getId());
        });

        verify(itemRepository, never()).findById(itemPencil.getId());
    }

    @Test
    void shouldReturnItemListHavingAllItemsWithNameMatchingTheGivenSearchString() {
        String searchString = "pen";
        when(itemRepository.findByNameContaining(searchString)).thenReturn(itemList);

        CartDTO resultantCartDTO = cartService.getItemListByNameBasedPattern(searchString);

        assertThat(resultantCartDTO, is(equalTo(cartDTO)));
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenNoItemWithNameHavingTheGivenPatternExists(){
        String searchString = "cat";
        when(itemRepository.findByNameContaining(searchString)).thenReturn(new ArrayList<Item>());

        assertThrows(ItemNotFoundException.class, () -> {
            cartService.getItemListByNameBasedPattern(searchString);
        });
    }

    @Test
    void shouldReturnEmptyListOfCartItems() {
        CartDTO cartDTO = new CartDTO(new ArrayList<>());
        when(itemRepository.findAll()).thenReturn(cartDTO.getItems());

        CartDTO resultantCartDTO = cartService.getItemList();

        assertThat(resultantCartDTO, is(equalTo(cartDTO)));
    }

    @Test
    void shouldReturnAllCartItems() {
        when(itemRepository.findAll()).thenReturn(cartDTO.getItems());

        CartDTO resultantCartDTO = cartService.getItemList();

        assertThat(resultantCartDTO, is(equalTo(cartDTO)));
    }

    @Test
    void shouldCallDeleteByIdMethodOfItemRepositoryWhenItemIsBeingDeletedUsingId() {
        when(itemRepository.existsById(itemPencil.getId())).thenReturn(true);

        cartService.deleteItemById(itemPencil.getId());

        verify(itemRepository, times(1)).deleteById(itemPencil.getId());
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhileDeletingItemUsingIdWhenTheItemIsNotAvailableInTheCart() {
        when(itemRepository.existsById(itemPencil.getId())).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> {
            cartService.deleteItemById(itemPencil.getId());
        });

        verify(itemRepository, never()).deleteById(itemPencil.getId());
    }

    @Test
    void shouldDeleteItemWhenGivenTheNameOfTheItem() {
        String name = "pencil";
        when(itemRepository.findByNameContaining(name)).thenReturn(itemList);

        cartService.deleteItemByName(name);

        verify(itemRepository).deleteByName(name);
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenNoItemWithGivenNameExists() {
        String name = "eraser";
        when(itemRepository.findByNameContaining(name)).thenReturn(new ArrayList<Item>());

        assertThrows(ItemNotFoundException.class, () -> {
            cartService.deleteItemByName(name);
        });

        verify(itemRepository, never()).deleteByName(name);
    }

}
