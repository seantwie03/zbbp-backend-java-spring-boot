package me.seantwiehaus.zbbp.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.request.TransactionRequest;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import me.seantwiehaus.zbbp.mapper.TransactionMapper;
import me.seantwiehaus.zbbp.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Validated
public class TransactionController {
  private final TransactionService service;
  private final TransactionMapper mapper;

  /**
   * @param startingDate The first Date to include in the list of results.
   *                     The default value is the first day of current month.
   *                     Formats: yyyy-MM-dd 2023-01-09 ; yyyy-M-d 2023-1-9
   * @param endingDate   The last Date to include in the list of results.
   *                     The default value is the current Date.
   * @return All Transactions between the starting and ending Dates (inclusive).
   */
  @GetMapping("/transactions")
  public List<TransactionResponse> getAllTransactionsBetween(
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = { "yyyy-M-d" }) Optional<LocalDate> startingDate,
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = { "yyyy-M-d" }) Optional<LocalDate> endingDate) {
    return service.getAllBetween(
                    startingDate.orElse(LocalDate.now().withDayOfMonth(1)),
                    endingDate.orElse(LocalDate.now()))
            .stream()
            .map(mapper::mapToResponse)
            .toList();
  }

  @GetMapping("/transactions/{id}")
  public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable @Min(0) Long id) {
    URI location = UriComponentsBuilder.fromPath("/transactions/{id}").buildAndExpand(id).toUri();
    Transaction transaction = service.getById(id);
    TransactionResponse response = mapper.mapToResponse(transaction);
    return ResponseEntity
            .ok()
            .location(location)
            .lastModified(response.lastModifiedAt())
            .body(response);
  }

  @PostMapping("/transactions")
  public ResponseEntity<TransactionResponse> createTransaction(
          @RequestBody @Valid TransactionRequest request) {
    Transaction requestTransaction = mapper.mapToDomain(request);
    Transaction transaction = service.create(requestTransaction);
    TransactionResponse response = mapper.mapToResponse(transaction);
    URI location = UriComponentsBuilder.fromPath("/transactions/{id}").buildAndExpand(response.id()).toUri();
    return ResponseEntity
            .created(location)
            .lastModified(response.lastModifiedAt())
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
            .lastModified(response.lastModifiedAt())
            .body(response);
  }

  @DeleteMapping("/transactions/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTransaction(@PathVariable @Min(0) Long id) {
    service.delete(id);
  }
}
