Table category_groups {
  id int [pk]
  name varchar
  budget_date date
  last_modified_at timestamptz
    indexes {
      (name, budget_date) [unique]
  }
}

Table categories {
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
  category_id int
}

Ref: categories.category_group_id > category_groups.id
Ref: transactions.category_id > categories.id
