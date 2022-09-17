package me.seantwiehaus.zbbp.controller;

import me.seantwiehaus.zbbp.dto.TransactionDto;
import me.seantwiehaus.zbbp.exception.NotFoundException;
import me.seantwiehaus.zbbp.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class TransactionController {

    TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/transactions")
    public List<TransactionDto> getAllTransactionsBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                          Optional<LocalDate> startDate,
                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                          Optional<LocalDate> endDate) {
        LocalDate start = startDate.orElse(LocalDate.now().minusYears(100));
        LocalDate end = endDate.orElse(LocalDate.now().plusYears(100));
        return service.getAllBetween(start, end)
                .map(TransactionDto::new)
                .toList();
    }

    @GetMapping("/transaction/{id}")
    public TransactionDto getTransactionById(@PathVariable Long id) {
        return service.findById(id)
                .map(TransactionDto::new)
                .orElseThrow(() -> new NotFoundException("Unable to find Transaction with Id: " + id));
    }

    @PostMapping("/transaction")
    public TransactionDto createTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        return new TransactionDto(service.create(transactionDto.convertToTransaction()));
    }

    @PutMapping("/transaction/{id}")
    public TransactionDto updateTransaction(@RequestBody @Valid TransactionDto transactionDto,
                                            @PathVariable Long id) {
        return service.update(id, transactionDto.convertToTransaction())
                .map(TransactionDto::new)
                .orElseThrow(() -> new NotFoundException("Unable to find Transaction with Id: " + id));
    }
}
