Table category_groups {
  id int [pk]
  name varchar
  budget_date date
    indexes {
      (name, budget_date) [unique]
  }
}

Table categories {
  id int [pk]
  name varchar
  planned_amount numeric(19,4)
  category_group_id int
  budget_date date
  indexes {
      (name, budget_date) [unique]
  }
}

Table categories_transactions {
  category_id int
  transaction_id int
  indexes {
      (category_id, transaction_id) [pk]
  }
}

Table transactionDtos {
  id int [pk]
  amount numeric(19,4)
  timestamp timestamp
  description varchar
}

Ref: categories.category_group_id > category_groups.id
Ref: categories.id > categories_transactions.category_id
Ref: transactionDtos.id < categories_transactions.transaction_id
