package com.practice.CartApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
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
    private CartDTO cartDTO;
    private ItemDTO itemPencilDTO;

    @BeforeEach
    void setUp() {
        itemPencil = new Item("Pencil", 20);
        cartDTO = new CartDTO(new ArrayList<>() {
            {
                add(itemPencil);
            }
        });
        itemPencilDTO = new ItemDTO("Pencil", 20);
    }

    @AfterEach
    void tearDown(){
        cartService.deleteItemById(itemPencil.getId());
    }

    @Test
    void shouldReturnEmptyCartWhenNoItemHasBeenAddedToTheCart() throws Exception {
        CartDTO emptyCartDTO = new CartDTO(new ArrayList<Item>());
        when(cartService.getItemList()).thenReturn(emptyCartDTO);

        mockMvc.perform(get("/cart/items"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(emptyCartDTO)))
                .andExpect(status().isOk());

        verify(cartService, times(1)).getItemList();
    }

    @Test
    void shouldReturnNonEmptyCartOfItems() throws Exception {
        when(cartService.getItemList()).thenReturn(cartDTO);

        mockMvc.perform(get("/cart/items"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cartDTO)))
                .andExpect(status().isOk());

        verify(cartService, times(1)).getItemList();
    }

    @Test
    void shouldReturnCartDTOOfItemsThatMatchesTheGivenPatternString() throws Exception {
        String searchString = "pen";
        when(cartService.getItemListByNameBasedPattern(searchString)).thenReturn(cartDTO);

        mockMvc.perform(get("/cart/items?name={name}", searchString))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cartDTO)))
                .andExpect(status().isOk());

        verify(cartService).getItemListByNameBasedPattern(searchString);
    }

    @Test
    void shouldReturnItemGivenTheItemId() throws Exception {
        when(cartService.getItemById(itemPencil.getId())).thenReturn(itemPencil);

        mockMvc.perform(get("/cart/items?id={id}", itemPencil.getId()))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cartDTO)))
                .andExpect(status().isOk());

        verify(cartService).getItemById(itemPencil.getId());
    }

    @Test
    void shouldReturnAllItemsWhenTheNameAndIdParametersAreEmptyStrings() throws Exception {
        when(cartService.getItemList()).thenReturn(cartDTO);

        mockMvc.perform(get("/cart/items?name={name}&id={id}", "", ""))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cartDTO)))
                .andExpect(status().isOk());

        verify(cartService, times(1)).getItemList();
    }

    @Test
    void shouldAddItemToCart() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO(itemPencil.getId(), itemPencil.getName() + " added to the cart.");
        when(cartService.addItem(itemPencilDTO)).thenReturn(itemPencil.getId());

        mockMvc.perform(post("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemPencilDTO)))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDTO)))
                        .andExpect(status().isCreated());

        verify(cartService).addItem(any());
    }

    @Test
    void shouldNotAddItemToCartWhenItemNameIsEmptyString() throws Exception {
        ErrorDTO errorDTO = new ErrorDTO(ErrorCode.INVALID_ITEM_NAME, "Item name should be non-empty string.");

        mockMvc.perform(post("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ItemDTO("", 0))))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(errorDTO)))
                        .andExpect(status().isBadRequest());

        verify(cartService, never()).addItem(any());
    }

    @Test
    void shouldRemoveItemFromCartWhenItemIdIsGiven() throws Exception {
        mockMvc.perform(delete("/cart/items/{id}", itemPencil.getId()))
                        .andExpect(content().json(new ObjectMapper().writeValueAsString(new ResponseDTO(itemPencil.getId(), "Item deleted from the cart."))))
                        .andExpect(status().isOk());

        verify(cartService).deleteItemById(itemPencil.getId());
    }
}
