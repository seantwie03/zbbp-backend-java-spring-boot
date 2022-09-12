package me.seantwiehaus.zbbp.controller;

import me.seantwiehaus.zbbp.domain.BudgetDate;
import me.seantwiehaus.zbbp.dto.CategoryGroupDto;
import me.seantwiehaus.zbbp.service.CategoryGroupService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryGroupController {
    private final CategoryGroupService service;

    public CategoryGroupController(CategoryGroupService service) {
        this.service = service;
    }

    /**
     * @param startBudgetDate Include all CategoryGroups with budgetDates greater-than-or-equal to this budgetDate.
     *                        BudgetDates are always on the 1st day of the month.
     *                        If no value is supplied, the default value will be the first day of the current month
     *                        100 years in the past.
     * @param endBudgetDate   Include all CategoryGroups with budgetDates less-than-or-equal-to this budgetDate.
     *                        BudgetDates are always on the 1st day of the month.
     *                        If no value is supplied, the default value will be the first day of the current month
     *                        100 years in the future.
     * @return All CategoryGroups with budgetDates between the start and end budgetDates (inclusive).
     */
    @GetMapping("/category-groups")
    public List<CategoryGroupDto> getAllCategoryGroupsBetween(@RequestParam
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                              Optional<LocalDate> startBudgetDate,
                                                              @RequestParam
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                              Optional<LocalDate> endBudgetDate) {
        BudgetDate start = startBudgetDate
                .map(BudgetDate::from)
                .orElse(BudgetDate.from(LocalDate.now().minusYears(100)));
        BudgetDate end = endBudgetDate
                .map(BudgetDate::from)
                .orElse(BudgetDate.from(LocalDate.now().plusYears(100)));
        return service.getAllCategoryGroupsBetween(start, end).stream()
                .map(CategoryGroupDto::new)
                .toList();
    }
}
