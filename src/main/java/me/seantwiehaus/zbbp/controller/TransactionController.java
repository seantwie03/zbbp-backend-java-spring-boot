package me.seantwiehaus.zbbp.controller;

import me.seantwiehaus.zbbp.dto.request.TransactionRequest;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import me.seantwiehaus.zbbp.exception.NotFoundException;
import me.seantwiehaus.zbbp.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class TransactionController {
    private static final String URI = "/transaction/";
    private static final String TRANSACTION = "Transaction";
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    /**
     * @param startDate Include transactions with dates greater-than-or-equal-to this day.
     *                  If no value is supplied, the default value will be the current day of the current month
     *                  100 years in the past.
     * @param endDate   Include transactions with dates less-than-or-equal-to this day.
     *                  If no value is supplied, the default value will be the current day of the current month
     *                  100 years in the future.
     * @return All transactions with dates between the start and end dates (inclusive)
     */
    @GetMapping("/transactions")
    public List<TransactionResponse> getAllTransactionsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate) {
        LocalDate start = startDate.orElse(LocalDate.now().minusYears(100));
        LocalDate end = endDate.orElse(LocalDate.now().plusYears(100));
        return service.getAllBetween(start, end)
                .stream()
                .map(TransactionResponse::new)
                .toList();
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) throws URISyntaxException {
        TransactionResponse response = service.findById(id)
                .map(TransactionResponse::new)
                .orElseThrow(() -> new NotFoundException(TRANSACTION, id));
        return ResponseEntity
                .ok()
                .location(new URI(URI + response.getId()))
                .lastModified(response.getLastModifiedAt())
                .body(response);
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody @Valid TransactionRequest request) throws URISyntaxException {
        TransactionResponse response =
                new TransactionResponse(service.create(request.convertToTransaction()));
        return ResponseEntity
                .created(new URI(URI + response.getId()))
                .lastModified(response.getLastModifiedAt())
                .body(response);
    }

    @PutMapping("/transaction/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @RequestBody @Valid TransactionRequest request,
            @PathVariable Long id,
            @RequestHeader("If-Unmodified-Since") Instant ifUnmodifiedSince) throws URISyntaxException {
        TransactionResponse response =
                service.update(id, ifUnmodifiedSince, request.convertToTransaction())
                        .map(TransactionResponse::new)
                        .orElseThrow(() -> new NotFoundException(TRANSACTION, id));
        return ResponseEntity
                .ok()
                .location(new URI(URI + response.getId()))
                .lastModified(response.getLastModifiedAt())
                .body(response);
    }

    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<Long> deleteTransaction(@PathVariable Long id) {
        return service.delete(id)
                .map(i -> ResponseEntity.ok(id))
                .orElseThrow(() -> new NotFoundException(TRANSACTION, id));
    }
}
