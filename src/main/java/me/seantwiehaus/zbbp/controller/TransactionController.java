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

    @GetMapping("/transactions")
    public List<TransactionResponse> getAllTransactionsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate) {
        LocalDate start = startDate.orElse(LocalDate.now().minusYears(100));
        LocalDate end = endDate.orElse(LocalDate.now().plusYears(100));
        return service.getAllBetween(start, end)
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
            @RequestBody @Valid TransactionRequest transactionRequest) throws URISyntaxException {
        TransactionResponse response =
                new TransactionResponse(service.create(transactionRequest.convertToTransaction()));
        return ResponseEntity
                .created(new URI(URI + response.getId()))
                .lastModified(response.getLastModifiedAt())
                .body(response);
    }

    @PutMapping("/transaction/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @RequestBody TransactionRequest transactionRequest,
            @PathVariable Long id,
            @RequestHeader("If-Unmodified-Since") Instant ifUnmodifiedSince) throws URISyntaxException {
        TransactionResponse response =
                service.update(id, ifUnmodifiedSince, transactionRequest.convertToTransaction())
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
