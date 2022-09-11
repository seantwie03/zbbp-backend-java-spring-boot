-- CategoryGroups
INSERT INTO category_groups (id, name, budget_date)
VALUES (1,
        'Incomes',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01))
ON CONFLICT DO NOTHING;

INSERT INTO category_groups (id, name, budget_date)
VALUES (2,
        'Food',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01))
ON CONFLICT DO NOTHING;

INSERT INTO category_groups (id, name, budget_date)
VALUES (3,
        'Utilities',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01))
ON CONFLICT DO NOTHING;

-- Categories
INSERT INTO categories (id, name, budget_date, planned_amount, category_group_id)
VALUES (1,
        'Primary Income',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        800.00,
        1)
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, planned_amount, category_group_id)
VALUES (2,
        'Secondary Income',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        800.00,
        1)
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, planned_amount, category_group_id)
VALUES (3,
        'Groceries',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        1600.00,
        2)
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, planned_amount, category_group_id)
VALUES (4,
        'Water / Sewer',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        150.00,
        3)
ON CONFLICT DO NOTHING;


-- Transactions
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (1,
        'Primary Employer 1', -- Paycheck 1
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        2100.00,
        true)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (2,
        'Transfer to savings XXX9989888', -- Savings Deposit 1
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        100.00,
        false)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (3,
        'Transfer to savings XXX999987', -- Savings Deposit 2
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        100.00,
        false)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (4,
        'Groceries 1 POS xxx989823', -- Groceries
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 05),
        400.00,
        false)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (5,
        'Fuel POS xxx989823',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 05),
        50.00,
        false)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (6,
        'Sharter Communications',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 11),
        50.00,
        false)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (7,
        'AquaMillion', -- Water bill
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 13),
        150.00,
        false)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (8,
        'Silent But Deadly', -- Sewer Bill
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 14),
        50.00,
        false)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (9,
        'Primary Employer 2', -- Paycheck 2
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        2100.00,
        true)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (10,
        'Transfer to savings XXX9989888', -- Savings Deposit 3
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        100.00,
        false)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (11,
        'Transfer to savings XXX999987', -- Savings Deposit 4
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        100.00,
        false)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (12,
        'Investment Maker', -- Roth IRA
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 19),
        400.00,
        false)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (13,
        'Morgage Payment 29832983', -- Mortgage
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 28),
        1400.00,
        false)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit)
VALUES (14,
        'Side Hustle Co.', -- Side Hustle Income 1
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 30),
        300.00,
        true)
ON CONFLICT DO NOTHING;

-- Categories_Transactions
INSERT INTO categories_transactions (category_id, transaction_id)
VALUES (1, -- Primary Income
        1) -- Paycheck 1
ON CONFLICT DO NOTHING;

INSERT INTO categories_transactions (category_id, transaction_id)
VALUES (1, -- Primary Income
        9) -- Paycheck 2
ON CONFLICT DO NOTHING;

INSERT INTO categories_transactions (category_id, transaction_id)
VALUES (2, -- Secondary Income
        14) -- Side Hustle
ON CONFLICT DO NOTHING;

INSERT INTO categories_transactions (category_id, transaction_id)
VALUES (3, -- Groceries
        4) -- Groceries 1
ON CONFLICT DO NOTHING;
