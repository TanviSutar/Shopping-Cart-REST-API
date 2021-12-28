package com.thoughtworks.CartApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.CartApp.custom_exceptions.ItemAlreadyExistsException;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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
    private Item itemEraser;
    private CartDTO cart;
    private ItemDTO itemDTOSharpener;

    @BeforeEach
    void setUp() {
        itemPencil = new Item("Pencil", 20);
        itemEraser = new Item("Eraser", 5);
        cart = new CartDTO(new ArrayList<>() {
            {
                add(itemPencil);
                add(itemEraser);
            }
        });
        itemDTOSharpener = new ItemDTO("Sharpener", 20);
    }

    @AfterEach
    void tearDown(){
        cartService.deleteItem(itemPencil.getId());
        cartService.deleteItem(itemEraser.getId());
    }

    @Test
    void shouldReturnListOfItems() throws Exception {
        when(cartService.viewItems()).thenReturn(cart);

        mockMvc.perform(get("/cart/items"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cart)))
                .andExpect(status().isOk());

        verify(cartService).viewItems();
    }

    @Test
    void shouldAddItemToCart() throws Exception {
        when(cartService.addItem(itemDTOSharpener)).thenReturn(1);

        mockMvc.perform(post("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemDTOSharpener)))
                        .andExpect(status().isCreated());

        verify(cartService).addItem(any());
    }

    //TODO review
    @Test
    void shouldNotAddDuplicateItemToCart() throws Exception {
        when(cartService.addItem(any())).thenThrow(new ItemAlreadyExistsException());

        mockMvc.perform(post("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemDTOSharpener)))
                        .andExpect(status().isNotFound());

        verify(cartService).addItem(any());
    }

    @Test
    void shouldRemoveItemFromCart() throws Exception {
        mockMvc.perform(delete("/cart/items/{id}", itemPencil.getId()))
                        .andExpect(status().isOk());

        verify(cartService).deleteItem(itemPencil.getId());
    }

    @Test
    void shouldReturnTwentyFiveAsTotalCostWhenPencilWorthTwentyRupeesAndEraserWorthFiveRupeesIsAddedToTheCart() throws Exception {
        when(cartService.viewItems()).thenReturn(cart);

        mockMvc.perform(get("/cart/items"))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cart)))
                .andExpect(status().isOk());

        verify(cartService).viewItems();
        assertThat(cart.getTotalCost(), is(equalTo(25.0)));
    }

    @Test
    void shouldReturnBadRequestStatusCodeWhenItemNameIsEmptyString() throws Exception {
        ItemDTO itemWithNoName = new ItemDTO(" ", 60.0);

        mockMvc.perform(post("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemWithNoName)))
                        .andExpect(status().isBadRequest());

        verify(cartService, never()).addItem(itemWithNoName);
    }

    @Test
    void shouldReturnItemGivenTheItemId() throws Exception {
        Item itemRasMalai = new Item("RasMalai", 1000);
        when(cartService.getItemById(itemRasMalai.getId())).thenReturn(itemRasMalai);

        mockMvc.perform(get("/cart/items?id={id}", itemRasMalai.getId()))
                .andExpect(status().isOk());

        verify(cartService).getItemById(itemRasMalai.getId());
    }

    //TODO review
    @Test
    void shouldReturnCartDTOOfItemsThatMatchTheGivenPatternString() throws Exception {
        String searchString = "pen";
        CartDTO cartDTO = new CartDTO(new ArrayList<>(){
            {
                add(itemPencil);
            }
        });
        when(cartService.searchByStringPattern(searchString)).thenReturn(cartDTO);

        mockMvc.perform(get("/cart/items?name={name}", searchString))
                .andExpect(status().isOk());

        verify(cartService).searchByStringPattern(searchString);
    }
}
