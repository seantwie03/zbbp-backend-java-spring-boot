package me.seantwiehaus.zbbp.controller;

import me.seantwiehaus.zbbp.dto.request.TransactionRequest;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import me.seantwiehaus.zbbp.exception.NotFoundException;
import me.seantwiehaus.zbbp.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class TransactionController {

    private static final String TRANSACTION_URI = "/transaction/";
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
        TransactionResponse transactionResponseDto = service.findById(id)
                .map(TransactionResponse::new)
                .orElseThrow(() -> new NotFoundException("Unable to find a Transaction with Id: " + id));
        return ResponseEntity
                .ok()
                .location(new URI(TRANSACTION_URI + transactionResponseDto.getId()))
                .lastModified(transactionResponseDto.getLastModifiedAt())
                .body(transactionResponseDto);
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody TransactionRequest transactionRequest) throws URISyntaxException {
        TransactionResponse transactionResponseDto =
                new TransactionResponse(service.create(transactionRequest.convertToTransaction()));

        return ResponseEntity
                .created(new URI(TRANSACTION_URI + transactionResponseDto.getId()))
                .lastModified(transactionResponseDto.getLastModifiedAt())
                .body(transactionResponseDto);
    }

    @PutMapping("/transaction/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @RequestBody TransactionRequest transactionRequest,
            @PathVariable Long id,
            @RequestHeader("If-Modified-Since") Instant ifModifiedSince) throws URISyntaxException {
        TransactionResponse transactionResponseDto =
                service.update(id, ifModifiedSince, transactionRequest.convertToTransaction())
                        .map(TransactionResponse::new)
                        .orElseThrow(() -> new NotFoundException("Unable to find a Transaction with Id: " + id));
        return ResponseEntity
                .ok()
                .location(new URI(TRANSACTION_URI + transactionResponseDto.getId()))
                .lastModified(transactionResponseDto.getLastModifiedAt())
                .body(transactionResponseDto);
    }
}
