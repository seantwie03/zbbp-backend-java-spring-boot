-- Insert mixed-case Type
-- This is used to test that the field is correctly serialized to the ItemType enums
insert into line_items (id, type, budget_date, name, planned_amount_cents, category, description, last_modified_at)
values (2, 'InCoMe', '2022-02-01',
        'Restaurants', 120000, 'INCOME',
        null, now());
