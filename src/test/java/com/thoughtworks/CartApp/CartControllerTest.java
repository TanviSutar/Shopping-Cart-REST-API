package com.thoughtworks.CartApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private Item itemPencil;
    private Item itemEraser;

    @BeforeEach
    void setUp() {
        itemPencil = new Item("Pencil", 20);
        itemEraser = new Item("Eraser", 5);
    }

    @Test
    void shouldReturnListOfItems() throws Exception {
        ArrayList<Item> itemList = new ArrayList<>() {
            {
                add(itemPencil);
                add(itemEraser);
            }
        };
        when(cartService.viewItems()).thenReturn(itemList);

        mockMvc.perform(get("/cart/items"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(itemList)))
                .andExpect(status().isOk());

        verify(cartService).viewItems();
    }

    @Test
    void shouldAddItemToCart() throws Exception {

        mockMvc.perform(post("/cart/items/{id}", itemPencil.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemPencil)))
                .andExpect(status().isCreated());

        verify(cartService).addItem(itemPencil);
    }

    @Test
    void shouldRemoveItemFromCart() throws Exception {
        mockMvc.perform(delete("/cart/items/{id}", itemPencil.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemPencil)))
                .andExpect(status().isOk());

        verify(cartService).deleteItem(itemPencil);
    }

    @Test
    void shouldReturnTwentyFiveAsTotalCostWhenPencilWorthTwentyRupeesAndEraserWorthFiveRupeesIsAddedToTheCart() throws Exception {
        when(cartService.totalCost()).thenReturn(25.0);

        mockMvc.perform(get("/cart/total-item-cost"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(25.0)))
                .andExpect(status().isOk());

        verify(cartService).totalCost();
    }
}
