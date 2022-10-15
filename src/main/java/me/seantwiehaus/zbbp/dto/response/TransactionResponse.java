package me.seantwiehaus.zbbp.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import me.seantwiehaus.zbbp.dto.serialize.CentsToDollarsSerializer;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
public class TransactionResponse {
  private final Long id;
  private final LocalDate date;
  private final String merchant;
  @JsonSerialize(using = CentsToDollarsSerializer.class)
  private final int amount;
  private final Long lineItemId;
  private final String description;
  private final Instant lastModifiedAt;
}
