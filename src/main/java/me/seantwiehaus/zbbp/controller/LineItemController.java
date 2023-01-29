package me.seantwiehaus.zbbp.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.dto.request.LineItemRequest;
import me.seantwiehaus.zbbp.dto.response.LineItemResponse;
import me.seantwiehaus.zbbp.mapper.LineItemMapper;
import me.seantwiehaus.zbbp.service.LineItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class LineItemController {
  private final LineItemService service;
  private final LineItemMapper mapper;

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
            .map(mapper::mapToResponse)
            .toList();
  }

  @GetMapping("/line-items/{id}")
  public ResponseEntity<LineItemResponse> getLineItemById(@PathVariable @Min(0) Long id) {
    URI location = UriComponentsBuilder.fromPath("/line-items/{id}").buildAndExpand(id).toUri();
    LineItem lineItem = service.findById(id);
    LineItemResponse response = mapper.mapToResponse(lineItem);
    return ResponseEntity
            .ok()
            .location(location)
            .lastModified(lineItem.lastModifiedAt())
            .body(response);
  }

  @PostMapping("/line-items")
  public ResponseEntity<LineItemResponse> createLineItem(
          @RequestBody @Valid LineItemRequest request) {
    LineItem requestLineItem = mapper.mapToDomain(request);
    LineItem lineItem = service.create(requestLineItem);
    LineItemResponse response = mapper.mapToResponse(lineItem);
    URI location = UriComponentsBuilder.fromPath("/line-items/{id}").buildAndExpand(lineItem.id()).toUri();
    return ResponseEntity
            .created(location)
            .lastModified(lineItem.lastModifiedAt())
            .body(response);
  }

  @PutMapping("/line-items/{id}")
  public ResponseEntity<LineItemResponse> updateLineItem(
          @RequestBody @Valid LineItemRequest request,
          @PathVariable @Min(0) Long id,
          @RequestHeader("If-Unmodified-Since") Instant ifUnmodifiedSince) {
    LineItem requestLineItem = mapper.mapToDomain(request);
    URI location = UriComponentsBuilder.fromPath("/line-items/{id}").buildAndExpand(id).toUri();
    LineItem lineItem = service.update(id, ifUnmodifiedSince, requestLineItem);
    LineItemResponse response = mapper.mapToResponse(lineItem);
    return ResponseEntity
            .ok()
            .location(location)
            .body(response);
  }

  @DeleteMapping("/line-items/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLineItem(@PathVariable @Min(0) Long id) {
    service.delete(id);
  }
}
