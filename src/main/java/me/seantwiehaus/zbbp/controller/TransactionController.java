package me.seantwiehaus.zbbp.controller;

import me.seantwiehaus.zbbp.dto.TransactionDto;
import me.seantwiehaus.zbbp.exception.NotFoundException;
import me.seantwiehaus.zbbp.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
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
        return service.getAllTransactionsBetween(start, end)
                .stream()
                .map(TransactionDto::new)
                .toList();
    }

    @GetMapping("/transaction/{id}")
    public TransactionDto getTransactionById(@PathVariable Long id) {
        return service.findById(id)
                .map(TransactionDto::new)
                .orElseThrow(() -> new NotFoundException("Unable to find Transactions for Id: " + id));
    }
}
