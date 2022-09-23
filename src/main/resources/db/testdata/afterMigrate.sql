-- Categories --
INSERT INTO categories (id, name, budget_date, last_modified_at)
VALUES (1,
        'Income',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, last_modified_at)
VALUES (2,
        'Savings',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, last_modified_at)
VALUES (3,
        'Housing',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, last_modified_at)
VALUES (4,
        'Transportation',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, last_modified_at)
VALUES (5,
        'Food',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, last_modified_at)
VALUES (6,
        'Personal',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, last_modified_at)
VALUES (7,
        'Lifestyle',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, last_modified_at)
VALUES (8,
        'Health',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;



-- Line Items --
-- Income
INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (100,
        'Husband Income',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        300000,
        1,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (101,
        'Wife Income',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        60000,
        1,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (102,
        'Side Hustle',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        30000,
        1,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Savings
INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (201,
        'Family Savings / Emergency Fund',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        10000,
        2,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (202,
        'Husband Savings',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        10000,
        2,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (203,
        'Wife Savings',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        10000,
        2,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (204,
        '529 College Savings Plan',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        50000,
        2,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (205,
        'Husband Roth IRA',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        50000,
        2,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (206,
        'Wife Roth IRA',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        50000,
        2,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Housing
INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (301,
        'Mortgage',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        130000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (302,
        'Additional Mortgage Payment',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        50000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (303,
        'Water / Sewer',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        15000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (304,
        'Natural Gas',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        4000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (305,
        'Electricity',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        120000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (306,
        'Trash',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        100000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (307,
        'Internet',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        5000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (308,
        'Cellphones',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        90000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (309,
        'Household Items',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        100000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (310,
        'Maintenance / Upkeep',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        0,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Transportation
INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (401,
        'Auto Insurance',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        12000,
        4,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (402,
        'Husband Fuel',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        4000,
        4,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (403,
        'Wife Fuel',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        8000,
        4,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (404,
        'Maintenance',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        0,
        4,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Food
INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (501,
        'Groceries / Consumables',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        12000,
        5,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (502,
        'Restaurants',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        10000,
        5,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Personal
INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (601,
        'Child Clothing',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        2000,
        6,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (602,
        'Husband Personal Expenses',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        30000,
        6,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (603,
        'Wife Personal Expenses',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        30000,
        6,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (604,
        'Husband Subscriptions',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        5000,
        6,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (605,
        'Wife Subscriptions',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        5000,
        6,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Lifestyle
INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (701,
        'Child Care',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        200000,
        7,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (702,
        'Pet Care',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        3000,
        7,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (703,
        'Entertainment',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        40000,
        7,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Health
INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (801,
        'Doctor Visits',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        0,
        8,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (802,
        'Prescription Drugs',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        3000,
        8,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, name, budget_date, planned_amount, category_id, last_modified_at)
VALUES (803,
        'Dentist Appointments',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        0,
        8,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Transactions --
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (1,
--        'Primary Employer 1', -- Paycheck 1
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--        210000,
--        true,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        1)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (2,
--        'Transfer to savings XXX9989888', -- Savings Deposit 1
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--        10000,
--        false,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        null)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (3,
--        'Transfer to savings XXX999987', -- Savings Deposit 2
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--        10000,
--        false,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        null)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (4,
--        'Groceries 1 POS xxx989823', -- Groceries
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 5),
--        40000,
--        false,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 5,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        3)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (5,
--        'Fuel POS xxx989823',
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 5),
--        5000,
--        false,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 5,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        null)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (6,
--        'Sharter Communications',
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 11),
--        5000,
--        false,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 11,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        null)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (7,
--        'AquaMillion', -- Water bill
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 13),
--        15000,
--        false,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 13,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        4)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (8,
--        'Silent But Deadly', -- Sewer Bill
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 14),
--        5000,
--        false,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 14,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        null)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (9,
--        'Primary Employer 2', -- Paycheck 2
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
--        210000,
--        true,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        1)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (10,
--        'Transfer to savings XXX9989888', -- Savings Deposit 3
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
--        10000,
--        false,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        null)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (11,
--        'Transfer to savings XXX999987', -- Savings Deposit 4
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
--        10000,
--        false,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        null)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (12,
--        'Investment Maker', -- Roth IRA
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 19),
--        40000,
--        false,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 19,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        null)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (13,
--        'Morgage Payment 29832983', -- Mortgage
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 28),
--        140000,
--        false,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 28,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        null)
--ON CONFLICT DO NOTHING;
--
--INSERT INTO transactions (id, description, date, amount, is_deposit, last_modified_at, line_item_id)
--VALUES (14,
--        'Side Hustle Co.', -- Side Hustle Income 1
--        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 30),
--        30000,
--        true,
--        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 30,
--                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                         extract(seconds from localtime), 'utc'),
--        2)
--ON CONFLICT DO NOTHING;
----
