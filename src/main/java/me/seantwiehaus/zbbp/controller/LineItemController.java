package me.seantwiehaus.zbbp.controller;

import lombok.RequiredArgsConstructor;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.dto.request.LineItemRequest;
import me.seantwiehaus.zbbp.dto.response.LineItemResponse;
import me.seantwiehaus.zbbp.service.LineItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class LineItemController {
  private final LineItemService service;

  /**
   * @param startingBudgetDate The first budgetDate to include in the list of results.
   *                           The default value is the current month 100 years in the past.
   * @param endingBudgetDate   The last budgetDate to include in the list of results.
   *                           The default value is the current month 100 years in the future.
   * @return All Line Items between the starting and ending budgetDates (inclusive).
   */
  @GetMapping("/line-items")
  public List<LineItemResponse> getAllLineItemsBetween(
      @RequestParam Optional<YearMonth> startingBudgetDate,
      @RequestParam Optional<YearMonth> endingBudgetDate) {
    return service.getAllBetween(
            startingBudgetDate.orElse(YearMonth.now().minusYears(100)),
            endingBudgetDate.orElse(YearMonth.now().plusYears(100)))
        .stream()
        .map(LineItemResponse::new)
        .toList();
  }

  @GetMapping("/line-items/{id}")
  public ResponseEntity<LineItemResponse> getLineItemById(@PathVariable @Min(0) Long id) {
    URI location = UriComponentsBuilder.fromPath("/line-items/{id}").buildAndExpand(id).toUri();
    LineItem lineItem = service.findById(id);
    return ResponseEntity
        .ok()
        .location(location)
        .lastModified(lineItem.getLastModifiedAt())
        .body(new LineItemResponse(lineItem));
  }

  @PostMapping("/line-items")
  public ResponseEntity<LineItemResponse> createLineItem(
      @RequestBody @Valid LineItemRequest request) {
    LineItem lineItem = service.create(request.convertToLineItem());
    URI location = UriComponentsBuilder.fromPath("/line-items/{id}").buildAndExpand(lineItem.getId()).toUri();
    return ResponseEntity
        .created(location)
        .lastModified(lineItem.getLastModifiedAt())
        .body(new LineItemResponse(lineItem));
  }

  @PutMapping("/line-items/{id}")
  public ResponseEntity<LineItemResponse> updateLineItem(
      @RequestBody @Valid LineItemRequest request,
      @PathVariable @Min(0) Long id,
      @RequestHeader("If-Unmodified-Since") Instant ifUnmodifiedSince) {
    URI location = UriComponentsBuilder.fromPath("/line-items/{id}").buildAndExpand(id).toUri();
    LineItem lineItem = service.update(id, ifUnmodifiedSince, request.convertToLineItem());
    return ResponseEntity
        .ok()
        .location(location)
        .body(new LineItemResponse(lineItem));
  }

  @DeleteMapping("/line-items/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLineItem(@PathVariable @Min(0) Long id) {
    service.delete(id);
  }
}
