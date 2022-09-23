package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Budget {
  List<Category> categories;
  Double totalIncome;
  Double totalPlanned;
  Double percentagePlanned;
  Double percentageLeftToPlan;
}
