-- Insert a LineItem with associated Transactions
-- This is used to test the sorting of Transactions
insert into line_items (id, budget_date, name, planned_amount, category, description, last_modified_at)
values (7878779, '2022-01-01', 'Groceries',
        120000, 'FOOD', null, now());

-- Insert the records out of order to ensure the sort i working properly
insert into transactions (id, date, merchant, amount, line_item_id, description, last_modified_at)
values (6969667, '2022-01-04', 'Walmart',
        3500, 7878779, null, now());
insert into transactions (id, date, merchant, amount, line_item_id, description, last_modified_at)
values (6969668, '2022-01-04', 'Walmart',
        3600, 7878779, null, now());
insert into transactions (id, date, merchant, amount, line_item_id, description, last_modified_at)
values (6969669, '2022-01-01', 'Schnucks',
        36000, 7878779, null, now());
