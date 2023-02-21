package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.exception.BadRequestException;
import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface DollarsToCentsMapper {
}

class DollarsToCentsConverter {
  private DollarsToCentsConverter() {
  }

  @DollarsToCentsMapper
  public static int convertDollarsToCents(double dollars) {
    if (dollars > Integer.MAX_VALUE / 100.0) {
      throw new BadRequestException("Dollars cannot be greater than " + Integer.MAX_VALUE / 100);
    }
    return Math.toIntExact(Math.round(dollars * 100));
  }
}
