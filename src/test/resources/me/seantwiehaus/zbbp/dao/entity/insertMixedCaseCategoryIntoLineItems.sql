-- Insert mixed-case Category
-- This is used to test that the field is correctly serialized to the Category enum
insert into line_items (id, type, budget_date, name, planned_amount_cents, category, description, last_modified_at)
values (1, 'EXPENSE', '2022-01-01',
        'Restaurants', 120000, 'fOOd',
        null, now());
