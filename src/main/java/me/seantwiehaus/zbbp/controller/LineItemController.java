package me.seantwiehaus.zbbp.controller;

import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dto.request.LineItemRequest;
import me.seantwiehaus.zbbp.dto.response.LineItemResponse;
import me.seantwiehaus.zbbp.exception.NotFoundException;
import me.seantwiehaus.zbbp.service.LineItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class LineItemController {
  private static final String URI = "/line-item/";
  private static final String LINE_ITEM = "Line Item";
  LineItemService service;

  public LineItemController(LineItemService service) {
    this.service = service;
  }

  /**
   * @param startingBudgetDate The first budgetDate to include in the list of results.
   *                           The default value is the current month 100 years in the past.
   * @param endingBudgetDate   The last budgetDate to include in the list of results.
   *                           If no value is supplied, the default value is the current month 100 years in the future.
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

  @GetMapping("/line-item/{id}")
  public ResponseEntity<LineItemResponse> getLineItemById(@PathVariable @Min(0) Long id) throws URISyntaxException {
    LineItemResponse response = service.findById(id)
        .map(LineItemResponse::new)
        .orElseThrow(() -> new NotFoundException(LINE_ITEM, id));
    return ResponseEntity
        .ok()
        .location(new URI(URI + response.getId()))
        .lastModified(response.getLastModifiedAt())
        .body(response);
  }

  @PostMapping("/line-item")
  public ResponseEntity<LineItemResponse> createLineItem(
      @RequestBody @Valid LineItemRequest request) throws URISyntaxException {
    LineItemResponse response = new LineItemResponse(service.create(request.convertToLineItem()));
    return ResponseEntity
        .created(new URI(URI + response.getId()))
        .lastModified(response.getLastModifiedAt())
        .body(response);
  }

  @PutMapping("/line-item/{id}")
  public ResponseEntity<LineItemResponse> updateLineItem(
      @RequestBody @Valid LineItemRequest request,
      @PathVariable @Min(0) Long id,
      @RequestHeader("If-Unmodified-Since") Instant ifUnmodifiedSince) throws URISyntaxException {
    LineItemResponse response = service.update(id, ifUnmodifiedSince, request.convertToLineItem())
        .map(LineItemResponse::new)
        .orElseThrow(() -> new NotFoundException(LINE_ITEM, id));
    return ResponseEntity
        .ok()
        .location(new URI(URI + response.getId()))
        .body(response);
  }

  @DeleteMapping("/line-item/{id}")
  public ResponseEntity<Long> deleteLineItem(@PathVariable @Min(0) Long id) {
    return service.delete(id)
        .map(i -> ResponseEntity.ok(id))
        .orElseThrow(() -> new NotFoundException(LINE_ITEM, id));
  }
}
