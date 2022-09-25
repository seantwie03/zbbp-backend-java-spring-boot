Table categories {
  id bigint [pk]
  budget_date date
  name varchar
  last_modified_at timestamptz
    indexes {
      (name, budget_date) [unique]
  }
}

Table line_items {
  id bigint [pk]
  budget_date date
  name varchar(50)
  planned_amount int
  category_id bigint
  description varchar(255)
  last_modified_at timestamptz
  indexes {
      (name, budget_date) [unique]
  }
}

Table transactions {
  id bigint [pk]
  date date
  merchant varchar(50)
  amount int
  is_income boolean
  line_item_id int
  description varchar
  last_modified_at timestamptz
}

Ref: line_items.category_id > categories.id
Ref: transactions.line_item_id > line_items.id
