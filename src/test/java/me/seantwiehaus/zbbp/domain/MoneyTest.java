package me.seantwiehaus.zbbp.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {
    private static Money money1;
    private static Money money2;

    @BeforeAll
    static void setup() {
        money1 = new Money(25.50);
        money2 = new Money(2550);
    }

    @Test
    void shouldReturnCorrectValueInCents() {
        assertEquals(2550, money1.inCents());
        assertEquals(2550, money2.inCents());
    }

    @Test
    void shouldReturnCorrectValueInDollars() {
        assertEquals(25.50, money1.inDollars());
        assertEquals(25.50, money2.inDollars());
    }

    @Test
    void shouldHaveEqualityWhenCreatedWithDifferentConstructors() {
        assertEquals(money1, money2);
    }
}
