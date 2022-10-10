package me.seantwiehaus.zbbp.dao.converter;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;

public class YearMonthDateAttributeConverter implements AttributeConverter<YearMonth, Date> {

  @Override
  public java.sql.Date convertToDatabaseColumn(YearMonth yearMonth) {
    if (yearMonth == null) {
      return null;
    }
    return java.sql.Date.valueOf(yearMonth.atDay(1));
  }

  @Override
  public YearMonth convertToEntityAttribute(java.sql.Date dbData) {
    if (dbData == null) {
      return null;
    }
    return YearMonth.from(Instant.ofEpochMilli(dbData.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
  }
}
