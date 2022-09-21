Table category_groups {
  id int [pk]
  name varchar
  budget_date date
  last_modified_at timestamptz
    indexes {
      (name, budget_date) [unique]
  }
}

Table line_items {
  id int [pk]
  name varchar
  budget_date date
  planned_amount numeric(19,4)
  last_modified_at timestamptz
  category_group_id int
  indexes {
      (name, budget_date) [unique]
  }
}

Table transactions {
  id int [pk]
  amount numeric(19,4)
  timestamp timestamp
  description varchar
  is_deposit boolean
  last_modified_at timestamptz
  line_item_id int
}

Ref: line_items.category_group_id > category_groups.id
Ref: transactions.line_item_id > line_items.id
