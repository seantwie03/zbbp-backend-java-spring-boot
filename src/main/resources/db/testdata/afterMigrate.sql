-- ItemTypes --
INSERT INTO item_types (type)
VALUES ('INCOME'),
       ('EXPENSE')
ON CONFLICT DO NOTHING;


-- Categories --
INSERT INTO categories (id, type, budget_date, name, last_modified_at)
VALUES (1,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Income',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (2,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Savings',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (3,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Housing',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (4,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Transportation',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (5,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Food',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (6,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Personal',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (7,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Lifestyle',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (8,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Health',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;



-- Line Items --
-- Income
INSERT INTO line_items (id, type, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (101,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Husband Income',
        300000,
        1,
        'Paid on 1st and 15th of every month',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, type, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (102,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Wife Income',
        60000,
        1,
        'Paid every 2 weeks',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, type, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (103,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Side Hustle',
        30000,
        1,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Savings
INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (201,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Family Savings / Emergency Fund',
        10000,
        2,
        'Keep a minimum of $6,000 for emergencies, use the rest as Family Savings account',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (202,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Husband Savings',
        10000,
        2,
        'We all know he is going to waste this on gadgets',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (203,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Wife Savings',
        10000,
        2,
        'AKA Craft and Cosmetics fund',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (204,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        '529 College Savings Plan',
        50000,
        2,
        'CollegeAmerica',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (205,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Husband Roth IRA',
        50000,
        2,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (206,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Wife Roth IRA',
        50000,
        2,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Housing
INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (301,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Mortgage',
        130000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (302,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Additional Mortgage Payment',
        50000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (303,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Water / Sewer',
        15000,
        3,
        'Paid every other month',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (304,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Natural Gas',
        4000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (305,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Electricity',
        120000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (306,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Trash',
        100000,
        3,
        'Paid Quarterly',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (307,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Internet',
        5000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (308,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Cellphones',
        90000,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (309,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Household Items',
        100000,
        3,
        'Non-Consumable household items. Tupperware, Linen, Storage Containers, etc.',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (310,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Maintenance / Upkeep',
        0,
        3,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Transportation
INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (401,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Auto Insurance',
        12000,
        4,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (402,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Husband Fuel',
        4000,
        4,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (403,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Wife Fuel',
        8000,
        4,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (404,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Maintenance',
        0,
        4,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Food
INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (501,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Groceries / Consumables',
        12000,
        5,
        'Things we consume.',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (502,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Restaurants',
        10000,
        5,
        'Mostly Qudoba',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Personal
INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (601,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Child Clothing',
        2000,
        6,
        '99 more Elsa dresses',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (602,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Husband Personal Expenses',
        30000,
        6,
        'Videogames or Keyboards, how to choose?',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (603,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Wife Personal Expenses',
        30000,
        6,
        'Nail polish technically counts as both a cosmetic and a craft supply',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (604,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Husband Subscriptions',
        5000,
        6,
        'Final World of Guild Wars 2',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, description, last_modified_at)
VALUES (605,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Wife Subscriptions',
        5000,
        6,
        'Extreme Baking Plus and Drag Tiny Home Makeover Streaming',
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Lifestyle
INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (701,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Child Care',
        200000,
        7,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (702,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Pet Care',
        3000,
        7,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (703,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Entertainment',
        40000,
        7,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Health
INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (801,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Doctor Visits',
        0,
        8,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (802,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Prescription Drugs',
        3000,
        8,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount, category_id, last_modified_at)
VALUES (803,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Dentist Appointments',
        0,
        8,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Transactions --
-- Income
INSERT INTO transactions (id, type, date, merchant, amount, line_item_id, last_modified_at)
VALUES (1001,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Husband Employer 1',
        310000,
        101,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, type, date, merchant, amount, line_item_id, last_modified_at)
VALUES (1002,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Wife Employer 1',
        40000,
        102,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, type, date, merchant, amount, line_item_id, last_modified_at)
VALUES (1003,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        'Husband Employer 2',
        310000,
        101,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, type, date, merchant, amount, line_item_id, last_modified_at)
VALUES (1004,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Wife Employer 2',
        40000,
        102,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, type, date, merchant, amount, line_item_id, last_modified_at)
VALUES (1005,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Side Hustle 1',
        30000,
        103,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


-- Savings
INSERT INTO transactions (id, date, merchant, amount, line_item_id, last_modified_at)
VALUES (2001,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Family Savings 1',
        5000,
        201,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, last_modified_at)
VALUES (2002,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Husband Savings 1',
        5000,
        202,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, last_modified_at)
VALUES (2003,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Wife Savings 1',
        5000,
        203,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, last_modified_at)
VALUES (2004,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        'Family Savings 2',
        5000,
        201,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, last_modified_at)
VALUES (2005,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        'Husband Savings 2',
        5000,
        202,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, last_modified_at)
VALUES (2006,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        'Wife Savings 2',
        5000,
        203,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, last_modified_at)
VALUES (2007,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 28),
        'AmericanCollegeFund',
        50000,
        204,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 28,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, last_modified_at)
VALUES (2008,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 28),
        'Husband IRA',
        50000,
        205,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 28,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, last_modified_at)
VALUES (2009,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 28),
        'Edward Jones IRA',
        50000,
        206,
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 28,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;
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
