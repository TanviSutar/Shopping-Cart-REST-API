package com.thoughtworks.CartApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class CartRepositoryTest {

    private CartRepository repository;
    private Item itemPencil;
    private Item itemErasure;

    @BeforeEach
    void setUp(){
        repository = new CartRepository();
        itemPencil = new Item("Pencil", 20);
        itemErasure = new Item("Erasure", 5);
    }

    @Test
    void shouldAddItemToCart() {

        repository.add(itemPencil);

        assertThat(repository.contains(itemPencil), is(equalTo(Boolean.TRUE)));
    }

    @Test
    void shouldDeleteItemInTheCart(){
        repository.add(itemPencil);

        repository.remove(itemPencil);

        assertThat(repository.contains(itemPencil), is(equalTo(Boolean.FALSE)));
    }

    @Test
    void shouldReturnTwentyFiveAsTotalCostOfPencilWorthRupeesTwentyAndErasureWorthRupeesFive() {
        repository.add(itemPencil);
        repository.add(itemErasure);

        assertThat(repository.totalCost(), is(closeTo(25, 0.001)));
    }
}
