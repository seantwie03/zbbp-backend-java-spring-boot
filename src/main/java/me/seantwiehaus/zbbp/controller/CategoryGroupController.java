package me.seantwiehaus.zbbp.controller;

import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.BudgetMonthRange;
import me.seantwiehaus.zbbp.dto.response.CategoryGroupResponse;
import me.seantwiehaus.zbbp.exception.NotFoundException;
import me.seantwiehaus.zbbp.service.CategoryGroupService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryGroupController {
  private static final String URI = "/category-group/";
  private static final String CATEGORY_GROUP = "CategoryGroup";
  private final CategoryGroupService service;

  public CategoryGroupController(CategoryGroupService service) {
    this.service = service;
  }

  /**
   * @param startBudgetDate Include all CategoryGroups with budgetDates greater-than-or-equal to this BudgetDate.
   *                        BudgetDates are always on the 1st day of the month.
   *                        If no value is supplied, the default value will be the first day of the current month
   *                        100 years in the past.
   * @param endBudgetDate   Include all CategoryGroups with budgetDates less-than-or-equal-to this BudgetDate.
   *                        BudgetDates are always on the 1st day of the month.
   *                        If no value is supplied, the default value will be the first day of the current month
   *                        100 years in the future.
   * @return All CategoryGroups with budgetDates between the startBudgetDate and endBudgetDate (inclusive).
   */
  @GetMapping("/category-groups")
  public List<CategoryGroupResponse> getAllCategoryGroupsBetween(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startBudgetDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endBudgetDate) {
    BudgetMonthRange budgetMonthRange = new BudgetMonthRange(
        startBudgetDate.map(BudgetMonth::new).orElse(null),
        endBudgetDate.map(BudgetMonth::new).orElse(null));
    return service.getAllBetween(budgetMonthRange)
        .stream()
        .map(CategoryGroupResponse::new)
        .toList();
  }

  @GetMapping("/category-group/{id}")
  public ResponseEntity<CategoryGroupResponse> getCategoryGroupById(@PathVariable Long id) throws URISyntaxException {
    CategoryGroupResponse response = service.findById(id)
        .map(CategoryGroupResponse::new)
        .orElseThrow(() -> new NotFoundException(CATEGORY_GROUP, id));
    return ResponseEntity
        .ok()
        .location(new URI(URI + response.getId()))
        .lastModified(response.getLastModifiedAt())
        .body(response);
  }
}
