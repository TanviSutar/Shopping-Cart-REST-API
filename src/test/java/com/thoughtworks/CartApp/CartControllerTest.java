package com.thoughtworks.CartApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class CartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnEmptyCart() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(get("/cart"))
                .andExpect(content().json(mapper.writeValueAsString(new Cart())))
                .andExpect(status().isOk());
    }


}