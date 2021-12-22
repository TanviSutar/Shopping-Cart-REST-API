package com.thoughtworks.CartApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    private Item itemPencil;

    @BeforeEach
    void setUp(){
        itemPencil = new Item("Pencil", 20);
    }

    @Test
    void shouldCallAddMethodOfCartRepositoryWhenValidItemIsBeingAdded() {
        cartService.addItem(itemPencil);

        verify(cartRepository, times(1)).add(itemPencil);
    }

    void shouldCallRemoveMethodOfCartRepositoryWhenItemIsBeingDeleted(){
        cartService.addItem(itemPencil);
        cartService.deleteItem(itemPencil);

        verify(cartRepository, times(1)).remove(itemPencil);
    }
}