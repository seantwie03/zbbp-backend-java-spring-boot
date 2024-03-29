package me.seantwiehaus.zbbp.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.dto.request.LineItemRequest;
import me.seantwiehaus.zbbp.dto.response.LineItemResponse;
import me.seantwiehaus.zbbp.mapper.LineItemMapper;
import me.seantwiehaus.zbbp.service.LineItemService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class LineItemController {
  private final LineItemService service;
  private final LineItemMapper mapper;

  /**
   * @param startingBudgetDate The first budgetDate to include in the list of results.
   *                           The default value is the current year-month.
   * @param endingBudgetDate   The last budgetDate to include in the list of results.
   *                           The default value is the current year-month.
   * @return All Line Items between the starting and ending budgetDates (inclusive).
   */
  @GetMapping("/line-items")
  public List<LineItemResponse> getAllLineItemsBetween(
          @RequestParam(defaultValue = "#{T(java.time.YearMonth).now()}")
          @DateTimeFormat(pattern = "yyyy-M")
          YearMonth startingBudgetDate,
          @RequestParam(defaultValue = "#{T(java.time.YearMonth).now()}")
          @DateTimeFormat(pattern = "yyyy-M")
          YearMonth endingBudgetDate) {
    return service.getAllBetween(startingBudgetDate, endingBudgetDate)
            .stream()
            .map(mapper::mapToResponse)
            .toList();
  }

  @GetMapping("/line-items/{id}")
  public ResponseEntity<LineItemResponse> getLineItemById(@PathVariable @Min(0) Long id) {
    URI location = UriComponentsBuilder.fromPath("/line-items/{id}").buildAndExpand(id).toUri();
    LineItem lineItem = service.getById(id);
    LineItemResponse response = mapper.mapToResponse(lineItem);
    return ResponseEntity
            .ok()
            .location(location)
            .lastModified(response.lastModifiedAt())
            .body(response);
  }

  @PostMapping("/line-items")
  public ResponseEntity<LineItemResponse> createLineItem(
          @RequestBody @Valid LineItemRequest request) {
    LineItem requestLineItem = mapper.mapToDomain(request);
    LineItem lineItem = service.create(requestLineItem);
    LineItemResponse response = mapper.mapToResponse(lineItem);
    URI location = UriComponentsBuilder.fromPath("/line-items/{id}").buildAndExpand(response.id()).toUri();
    return ResponseEntity
            .created(location)
            .lastModified(response.lastModifiedAt())
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
            .lastModified(response.lastModifiedAt())
            .body(response);
  }

  @DeleteMapping("/line-items/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLineItem(@PathVariable @Min(0) Long id) {
    service.delete(id);
  }
}
