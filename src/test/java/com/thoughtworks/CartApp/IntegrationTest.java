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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void shouldReturnEmptyItemListBeforeAnyItemIsAddedToTheCart() throws Exception {
        mockMvc.perform(get("/cart/items"))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(new CartDTO(new ArrayList<Item>()))))
                        .andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfAllItemsInTheCart() throws Exception {
        Item itemBook = new Item("Book", 400);
        Item itemPen = new Item("Pen", 30);
        CartDTO cartDTO = new CartDTO(new ArrayList<>(){
            {
                add(itemBook);
                add(itemPen);
            }
        });
        itemRepository.save(itemBook);
        itemRepository.save(itemPen);

        mockMvc.perform(get("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(cartDTO)))
                        .andExpect(status().isOk());

        itemRepository.deleteById(itemBook.getId());
        itemRepository.deleteById(itemPen.getId());
    }

    @Test
    void shouldAddItemToTheCart() throws Exception {
        ItemDTO itemLettuce = new ItemDTO("lettuce", 40.0);

        mockMvc.perform(post("/cart/items", itemLettuce.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemLettuce)))
                        .andExpect(status().isCreated());

        assertThat(itemRepository.existsByName(itemLettuce.getName()), is(equalTo(Boolean.TRUE)));
    }

    @Test
    void shouldDeleteItemFromTheCart() throws Exception {
        Item itemPencil = new Item("pencil", 20);
        itemRepository.save(itemPencil);
        mockMvc.perform(delete("/cart/items/{id}", itemPencil.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        assertThat(itemRepository.existsById(itemPencil.getId()), is(equalTo(Boolean.FALSE)));
    }
}
