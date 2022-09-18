-- CategoryGroups
INSERT INTO category_groups (id, name, budget_date, last_modified_at)
VALUES (1,
        'Incomes',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        timezone('utc', now()))
ON CONFLICT DO NOTHING;

INSERT INTO category_groups (id, name, budget_date, last_modified_at)
VALUES (2,
        'Food',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        timezone('utc', now()))
ON CONFLICT DO NOTHING;

INSERT INTO category_groups (id, name, budget_date, last_modified_at)
VALUES (3,
        'Utilities',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        timezone('utc', now()))
ON CONFLICT DO NOTHING;

-- Categories
INSERT INTO categories (id, name, budget_date, planned_amount, category_group_id, last_modified_at)
VALUES (1,
        'Primary Income',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        600000,
        1,
        timezone('utc', now()))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, planned_amount, category_group_id, last_modified_at)
VALUES (2,
        'Secondary Income',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        30000,
        1,
        timezone('utc', now()))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, planned_amount, category_group_id, last_modified_at)
VALUES (3,
        'Groceries',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        160000,
        2,
        timezone('utc', now()))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, planned_amount, category_group_id, last_modified_at)
VALUES (4,
        'Water / Sewer',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        15000,
        3,
        timezone('utc', now()))
ON CONFLICT DO NOTHING;


-- Transactions
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (1,
        'Primary Employer 1', -- Paycheck 1
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        210000,
        true,
        timezone('utc', now()),
        1)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (2,
        'Transfer to savings XXX9989888', -- Savings Deposit 1
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        10000,
        false,
        timezone('utc', now()),
        null)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (3,
        'Transfer to savings XXX999987', -- Savings Deposit 2
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 01),
        10000,
        false,
        timezone('utc', now()),
        null)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (4,
        'Groceries 1 POS xxx989823', -- Groceries
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 05),
        40000,
        false,
        timezone('utc', now()),
        3)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (5,
        'Fuel POS xxx989823',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 05),
        5000,
        false,
        timezone('utc', now()),
        null)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (6,
        'Sharter Communications',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 11),
        5000,
        false,
        timezone('utc', now()),
        null)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (7,
        'AquaMillion', -- Water bill
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 13),
        15000,
        false,
        timezone('utc', now()),
        4)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (8,
        'Silent But Deadly', -- Sewer Bill
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 14),
        5000,
        false,
        timezone('utc', now()),
        null)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (9,
        'Primary Employer 2', -- Paycheck 2
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        210000,
        true,
        timezone('utc', now()),
        1)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (10,
        'Transfer to savings XXX9989888', -- Savings Deposit 3
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        10000,
        false,
        timezone('utc', now()),
        null)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (11,
        'Transfer to savings XXX999987', -- Savings Deposit 4
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        10000,
        false,
        timezone('utc', now()),
        null)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (12,
        'Investment Maker', -- Roth IRA
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 19),
        40000,
        false,
        timezone('utc', now()),
        null)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (13,
        'Morgage Payment 29832983', -- Mortgage
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 28),
        140000,
        false,
        timezone('utc', now()),
        null)
ON CONFLICT DO NOTHING;
INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, category_id)
VALUES (14,
        'Side Hustle Co.', -- Side Hustle Income 1
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 30),
        30000,
        true,
        timezone('utc', now()),
        2)
ON CONFLICT DO NOTHING;
