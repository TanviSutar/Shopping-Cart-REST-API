package com.thoughtworks.CartApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private Item itemPencil, itemEraser;
    private Cart cart;

    @BeforeEach
    void setUp() throws Exception {
        itemPencil = new Item("pencil", 20.0);
        itemEraser = new Item("eraser", 5.0);
        cart = new Cart(new ArrayList<>(){
            {
                add(itemPencil);
                add(itemEraser);
            }
        });
        mockMvc.perform(post("/cart/items/{id}", itemPencil.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemPencil)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/cart/items/{id}", itemEraser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemEraser)))
                .andExpect(status().isCreated());
    }

    @Test
    @Disabled
    void shouldReturnEmptyItemListBeforeAnyItemIsAddedToTheCart() throws Exception {
        mockMvc.perform(get("/cart/items"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(new Cart(new ArrayList<Item>()))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddItemToTheCart() throws Exception {
        Item itemLettuce = new Item("Lettuce", 40.0);
        mockMvc.perform(post("/cart/items/{id}", itemLettuce.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemLettuce)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnListOfAllItemsInTheCart() throws Exception {

        mockMvc.perform(get("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cart)))
                .andExpect(status().isOk());
    }
}
