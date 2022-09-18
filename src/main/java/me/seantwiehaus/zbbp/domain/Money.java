package me.seantwiehaus.zbbp.domain;

import lombok.ToString;

import java.util.Objects;

@ToString
public class Money {
    private final Integer amountInCents;

    public Money(Double amountInDollars) {
        this.amountInCents = Math.toIntExact(Math.round(amountInDollars * 100));
    }

    public Money(Integer amountInCents) {
        this.amountInCents = amountInCents;
    }

    public Integer inCents() {
        return amountInCents;
    }

    public Double inDollars() {
        return amountInCents / 100.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amountInCents.equals(money.amountInCents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountInCents);
    }
}
