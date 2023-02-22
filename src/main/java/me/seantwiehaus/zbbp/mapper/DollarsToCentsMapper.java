package me.seantwiehaus.zbbp.mapper;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@interface DollarsToCentsMapper {
}

class DollarsToCentsConverter {
  private DollarsToCentsConverter() {
  }

  @DollarsToCentsMapper
  public static int convertDollarsToCents(double dollars) {
    return Math.toIntExact(Math.round(dollars * 100));
  }
}
