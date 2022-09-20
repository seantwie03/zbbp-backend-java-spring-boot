package me.seantwiehaus.zbbp.domain;

import lombok.ToString;

import java.util.Objects;

@ToString
public class Money {
  private final int amountInCents;

  public Money(double amountInDollars) {
    this.amountInCents = Math.toIntExact(Math.round(amountInDollars * 100));
  }

  public Money(int amountInCents) {
    this.amountInCents = amountInCents;
  }

  public int inCents() {
    return amountInCents;
  }

  public double inDollars() {
    return amountInCents / 100.0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Money money = (Money) o;
    return amountInCents == money.amountInCents;
  }

  @Override
  public int hashCode() {
    return Objects.hash(amountInCents);
  }
}
