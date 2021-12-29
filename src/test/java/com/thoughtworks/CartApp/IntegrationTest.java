package com.thoughtworks.CartApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO review
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartService cartService;

    @Test
    void shouldReturnEmptyItemListBeforeAnyItemIsAddedToTheCart() throws Exception {
        mockMvc.perform(get("/cart/items"))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(new CartDTO(new ArrayList<Item>()))))
                        .andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfAllItemsInTheCart() throws Exception {
        ItemDTO itemDTO = new ItemDTO("fan", 1000);

        mockMvc.perform(post("/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(itemDTO)));

        mockMvc.perform(get("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    void shouldAddItemToTheCart() throws Exception {
        Item itemLettuce = new Item("lettuce", 40.0);

        mockMvc.perform(post("/cart/items", itemLettuce.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemLettuce)))
                        .andExpect(status().isCreated());

    }

    @Test
    void shouldDeleteItemFromTheCart() throws Exception {
        ItemDTO itemPencil = new ItemDTO("Pencil", 20);
        int id = cartService.addItem(itemPencil);
        mockMvc.perform(delete("/cart/items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}
