-- Insert mixed-case Type and Category
-- This is used to test that those fields are correctly serialized to the ItemType and Category enums
insert into line_items (id, type, budget_date, name, planned_amount, category, description, last_modified_at)
values (7878778, 'expeNSe', '2022-01-01',
        'Groceries', 120000, 'fOOd',
        null, now());
