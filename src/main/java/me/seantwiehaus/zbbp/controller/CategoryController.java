package me.seantwiehaus.zbbp.controller;

import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.BudgetMonthRange;
import me.seantwiehaus.zbbp.dto.response.CategoryResponse;
import me.seantwiehaus.zbbp.exception.NotFoundException;
import me.seantwiehaus.zbbp.service.CategoryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
  private static final String URI = "/category/";
  private static final String CATEGORY = "Category";
  private final CategoryService service;

  public CategoryController(CategoryService service) {
    this.service = service;
  }

  /**
   * @param startBudgetDate Include all Categories with budgetDates greater-than-or-equal to this BudgetDate.
   *                        BudgetDates are always on the 1st day of the month.
   *                        If no value is supplied, the default value will be the first day of the current month
   *                        100 years in the past.
   * @param endBudgetDate   Include all Categories with budgetDates less-than-or-equal-to this BudgetDate.
   *                        BudgetDates are always on the 1st day of the month.
   *                        If no value is supplied, the default value will be the first day of the current month
   *                        100 years in the future.
   * @return All Categories with budgetDates between the startBudgetDate and endBudgetDate (inclusive).
   */
  @GetMapping("/categories")
  public List<CategoryResponse> getAllCategoriesBetween(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startBudgetDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endBudgetDate) {
    BudgetMonthRange budgetMonthRange = new BudgetMonthRange(
        startBudgetDate.map(BudgetMonth::new).orElse(null),
        endBudgetDate.map(BudgetMonth::new).orElse(null));
    return service.getAllBetween(budgetMonthRange)
        .stream()
        .map(CategoryResponse::new)
        .toList();
  }

  @GetMapping("/category/{id}")
  public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable @Min(0) Long id) throws URISyntaxException {
    CategoryResponse response = service.findById(id)
        .map(CategoryResponse::new)
        .orElseThrow(() -> new NotFoundException(CATEGORY, id));
    return ResponseEntity
        .ok()
        .location(new URI(URI + response.getId()))
        .lastModified(response.getLastModifiedAt())
        .body(response);
  }
}
