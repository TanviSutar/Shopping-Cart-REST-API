package com.thoughtworks.CartApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Item itemErasure = new Item("Erasure", 20);
        cartService.addItem(itemErasure);

        verify(cartRepository, times(1)).add(itemErasure);
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
}
