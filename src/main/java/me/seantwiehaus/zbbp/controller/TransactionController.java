package me.seantwiehaus.zbbp.controller;

import lombok.RequiredArgsConstructor;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.request.TransactionRequest;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import me.seantwiehaus.zbbp.mapper.TransactionMapper;
import me.seantwiehaus.zbbp.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class TransactionController {
  private final TransactionService service;
  private final TransactionMapper mapper;

  /**
   * @param startingDate The first Date to include in the list of results.
   *                     The default value is the current Date 100 years in the past.
   * @param endingDate   The last Date to include in the list of results.
   *                     The default value is the current Date 100 years in the future.
   * @return All Transactions between the starting and ending Dates (inclusive).
   */
  @GetMapping("/transactions")
  public List<TransactionResponse> getAllTransactionsBetween(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startingDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endingDate) {
    return service.getAllBetween(
            startingDate.orElse(LocalDate.now().minusYears(100)),
            endingDate.orElse(LocalDate.now().plusYears(100)))
        .stream()
        .map(mapper::mapToResponse)
        .toList();
  }

  @GetMapping("/transactions/{id}")
  public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable @Min(0) Long id) {
    URI location = UriComponentsBuilder.fromPath("/transactions/{id}").buildAndExpand(id).toUri();
    Transaction transaction = service.findById(id);
    TransactionResponse response = mapper.mapToResponse(transaction);
    return ResponseEntity
        .ok()
        .location(location)
        .lastModified(transaction.lastModifiedAt())
        .body(response);
  }

  @PostMapping("/transactions")
  public ResponseEntity<TransactionResponse> createTransaction(
      @RequestBody @Valid TransactionRequest request) {
    Transaction requestTransaction = mapper.mapToDomain(request);
    Transaction transaction = service.create(requestTransaction);
    TransactionResponse response = mapper.mapToResponse(transaction);
    URI location = UriComponentsBuilder.fromPath("/transactions/{id}").buildAndExpand(transaction.id()).toUri();
    return ResponseEntity
        .created(location)
        .lastModified(transaction.lastModifiedAt())
        .body(response);
  }

  @PutMapping("/transactions/{id}")
  public ResponseEntity<TransactionResponse> updateTransaction(
      @RequestBody @Valid TransactionRequest request,
      @PathVariable @Min(0) Long id,
      @RequestHeader("If-Unmodified-Since") Instant ifUnmodifiedSince) {
    URI location = UriComponentsBuilder.fromPath("/transactions/{id}").buildAndExpand(id).toUri();
    Transaction requestTransaction = mapper.mapToDomain(request);
    Transaction transaction = service.update(id, ifUnmodifiedSince, requestTransaction);
    TransactionResponse response = mapper.mapToResponse(transaction);
    return ResponseEntity
        .ok()
        .location(location)
        .lastModified(transaction.lastModifiedAt())
        .body(response);
  }

  @DeleteMapping("/transactions/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTransaction(@PathVariable @Min(0) Long id) {
    service.delete(id);
  }
}
