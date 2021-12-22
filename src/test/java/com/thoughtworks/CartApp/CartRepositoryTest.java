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
    private Item itemOne;
    private Item itemTwo;

    @BeforeEach
    void setUp(){
        repository = new CartRepository();
        itemOne = new Item("Pencil", 20);
        itemTwo = new Item("Erasure", 5);
    }

    @Test
    void shouldAddItemToCart() {

        repository.add(itemOne);

        assertThat(repository.contains(itemOne), is(equalTo(Boolean.TRUE)));
    }

    @Test
    void shouldDeleteItemInTheCart(){
        repository.add(itemOne);

        repository.remove(itemOne);

        assertThat(repository.contains(itemOne), is(equalTo(Boolean.FALSE)));
    }

    @Test
    void shouldReturnTwentyFiveAsTotalCostOfPencilWorthRupeesTwentyAndErasureWorthRupeesFive() {
        repository.add(itemOne);
        repository.add(itemTwo);

        assertThat(repository.totalCost(), is(closeTo(25, 0.001)));
    }
}
