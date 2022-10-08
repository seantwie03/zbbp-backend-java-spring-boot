-- Insert three LineItems with different dates
-- This is used to test a queries that fetch LineItem(s) by filtering on budget_date
insert into line_items (id, budget_date, name, planned_amount, category, description, last_modified_at)
values (7878779, '2022-01-01', 'Groceries',
        120000, 'FOOD', null, now());

insert into line_items (id, budget_date, name, planned_amount, category, description, last_modified_at)
values (7878780, '2022-02-01', 'Groceries',
        120000, 'FOOD', null, now());

insert into line_items (id, budget_date, name, planned_amount, category, description, last_modified_at)
values (7878781, '2022-03-01', 'Groceries',
        120000, 'FOOD', null, now());
