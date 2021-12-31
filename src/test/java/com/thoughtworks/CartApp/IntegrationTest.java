package com.thoughtworks.CartApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    private static Item itemPencil;
    private static Item itemEraser;

    @BeforeAll
    static void setUp(){
        itemPencil = new Item("pencil", 20);
        itemEraser = new Item("eraser", 10);
    }

    @Test
    void shouldReturnEmptyItemListBeforeAnyItemIsAddedToTheCart() throws Exception {
        mockMvc.perform(get("/cart/items"))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(new CartDTO(new ArrayList<Item>()))))
                        .andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfAllItemsInTheCart() throws Exception {
        CartDTO cartDTO = new CartDTO(new ArrayList<Item>(){
            {
                add(itemPencil);
                add(itemEraser);
            }
        });
        itemRepository.save(itemPencil);
        itemRepository.save(itemEraser);

        mockMvc.perform(get("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(cartDTO)))
                        .andExpect(status().isOk());

        itemRepository.deleteById(itemPencil.getId());
        itemRepository.deleteById(itemEraser.getId());
    }

    @Test
    void shouldReturnCARTDTOOfItemsHavingNameThatMatchesTheGivenPattern() throws Exception {
        itemRepository.save(itemPencil);
        itemRepository.save(itemEraser);

        mockMvc.perform(get("/cart/items?name={name}", "pen")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(new CartDTO(new ArrayList<>(){
                            {
                                add(itemPencil);
                            }
                        }))))
                        .andExpect(status().isOk());

        itemRepository.deleteById(itemPencil.getId());
        itemRepository.deleteById(itemEraser.getId());
    }

    @Test
    void shouldReturnCardDTOOfItemHavingTheGivenItemId() throws Exception {
        itemRepository.save(itemPencil);
        itemRepository.save(itemEraser);

        mockMvc.perform(get("/cart/items?id={id}", itemPencil.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(new CartDTO(new ArrayList<>(){
                            {
                                add(itemPencil);
                            }
                        }))))
                        .andExpect(status().isOk());

        itemRepository.deleteById(itemPencil.getId());
        itemRepository.deleteById(itemEraser.getId());
    }

    @Test
    void shouldAddValidItemToTheCart() throws Exception {
        ItemDTO itemLettuce = new ItemDTO("lettuce", 40.0);

        mockMvc.perform(post("/cart/items", itemLettuce.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemLettuce)))
                        .andExpect(status().isCreated());

        assertThat(itemRepository.existsByName(itemLettuce.getName()), is(equalTo(Boolean.TRUE)));
    }

    @Test
    void shouldNotAddItemWithInvalidName() throws Exception {
        ErrorDTO errorDTO = new ErrorDTO(ErrorCode.INVALID_ITEM_NAME, "Item name should be non-empty string.");
        ItemDTO itemBook = new ItemDTO("", 90.0);

        mockMvc.perform(post("/cart/items", itemBook.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemBook)))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(errorDTO)))
                        .andExpect(status().isBadRequest());

        assertThat(itemRepository.existsByName(itemBook.getName()), is(equalTo(Boolean.FALSE)));
    }

    @Test
    void shouldNotAddDuplicateItem() throws Exception {
        ErrorDTO errorDTO = new ErrorDTO(ErrorCode.ITEM_ALREADY_EXISTS, "A similar item already exists in the cart.");
        itemRepository.save(itemPencil);

        mockMvc.perform(post("/cart/items", itemPencil.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemPencil)))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(errorDTO)))
                        .andExpect(status().isConflict());

        itemRepository.deleteById(itemPencil.getId());
    }

    @Test
    void shouldDeleteItemFromTheCart() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO(itemPencil.getId(), "Item deleted from the cart.");
        itemRepository.save(itemPencil);

        mockMvc.perform(delete("/cart/items/{id}", itemPencil.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        assertThat(itemRepository.existsById(itemPencil.getId()), is(equalTo(Boolean.FALSE)));
    }

    @Test
    void shouldReturnItemNotFoundErrorDTOWhenNoItemWithIdExists() throws Exception {
        ErrorDTO errorDTO = new ErrorDTO(ErrorCode.ITEM_NOT_FOUND, "No such item present in the cart.");

        mockMvc.perform(delete("/cart/items/{id}", itemPencil.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(errorDTO)))
                        .andExpect(status().isNotFound());
    }
}
