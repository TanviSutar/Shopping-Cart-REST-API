package com.thoughtworks.CartApp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class CartRepositoryTest {

    @Test
    void shouldAddItemToCart() {
        CartRepository repository = new CartRepository();
        Item newItem = new Item("Lollypop", 5);

        repository.add(newItem);

        assertThat(repository.contains(newItem), is(equalTo(Boolean.FALSE)));
    }

    @Test
    void shouldDeleteItemInTheCart(){
        CartRepository repository = new CartRepository();
        Item item = new Item("Pencil", 10);
        repository.add(item);

        repository.remove(item);

        assertThat(repository.contains(item), is(equalTo(Boolean.FALSE)));
    }

    @Test
    void shouldReturnTwentyFiveAsTotalCostOfPencilWorthRupeesTwentyAndErasureWorthRupeesFive() {
        CartRepository repository = new CartRepository();
        repository.add(new Item("Pencil", 20));
        repository.add(new Item("Erasure", 5));

        assertThat(repository.totalCost(), is(closeTo(25, 0.001)));
    }
}
