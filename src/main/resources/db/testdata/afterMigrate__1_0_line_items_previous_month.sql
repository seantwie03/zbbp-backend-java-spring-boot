-- Line Items --
-- Previous Month


--
-- Income
--
INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
VALUES (100,
        make_date(
                extract(year FROM current_date)::int, -- Year
                extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                1), -- Day,
        'Spouse 1 Income',
        300000,
        'INCOME',
        'Paid on 1st and 15th of every month',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
VALUES (101,
        make_date(
                extract(year FROM current_date)::int, -- Year
                extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                1), -- Day
        'Spouse 2 Income',
        250000,
        'INCOME',
        'Paid every 2 weeks',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
VALUES (102,
        make_date(
                extract(year FROM current_date)::int, -- Year
                extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                1), -- Day
        'Side Hustle - Photography',
        50000,
        'INCOME',
        'Weasley Wedding',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;


--
-- Savings
--
INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
VALUES (201,
        make_date(
                extract(year FROM current_date)::int, -- Year
                extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                1), -- Day
        'Emergency Fund / Family Savings',
        10000,
        'SAVINGS',
        'Keep at least $2,000 for emergencies, use the rest as a Family Savings account',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
VALUES (202,
        make_date(
                extract(year FROM current_date)::int, -- Year
                extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                1), -- Day
        'Spouse 1 Savings',
        10000,
        'SAVINGS',
        '100% chance that this will be used to buy gadgets.',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
VALUES (203,
        make_date(
                extract(year FROM current_date)::int, -- Year
                extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                1), -- Day
        'Spouse 2 Savings',
        10000,
        'SAVINGS',
        'AKA crafts and cosmetics fund',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
VALUES (204,
        make_date(
                extract(year FROM current_date)::int, -- Year
                extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                1), -- Day
        '529 College Savings Plan',
        50000,
        'INVESTMENTS',
        'College America',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;


-- Housing
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (301,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Mortgage',
--         130000,
--         3,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (302,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Additional Mortgage Payment',
--         50000,
--         3,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
-- VALUES (303,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Water / Sewer',
--         15000,
--         3,
--         'Paid every other month',
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (304,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Natural Gas',
--         4000,
--         3,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (305,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Electricity',
--         120000,
--         3,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
-- VALUES (306,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Trash',
--         100000,
--         3,
--         'Paid Quarterly',
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (307,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Internet',
--         5000,
--         3,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (308,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Cellphones',
--         90000,
--         3,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
-- VALUES (309,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Household Items',
--         100000,
--         3,
--         'Non-Consumable household items. Tupperware, Linen, Storage Containers, etc.',
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (310,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Maintenance / Upkeep',
--         0,
--         3,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
--
-- -- Transportation
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (401,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Auto Insurance',
--         12000,
--         4,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (402,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Husband Fuel',
--         4000,
--         4,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (403,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Wife Fuel',
--         8000,
--         4,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (404,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Maintenance',
--         0,
--         4,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
--
-- -- Food
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
-- VALUES (501,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Groceries / Consumables',
--         12000,
--         5,
--         'Things we consume.',
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
-- VALUES (502,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Restaurants',
--         10000,
--         5,
--         'Mostly Qudoba',
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
--
-- -- Personal
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
-- VALUES (601,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Child Clothing',
--         2000,
--         6,
--         '99 more Elsa dresses',
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
-- VALUES (602,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Husband Personal Expenses',
--         30000,
--         6,
--         'Videogames or Keyboards, how to choose?',
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
-- VALUES (603,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Wife Personal Expenses',
--         30000,
--         6,
--         'Nail polish technically counts as both a cosmetic and a craft supply',
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
-- VALUES (604,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Husband Subscriptions',
--         5000,
--         6,
--         'Final World of Guild Wars 2',
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, description, last_modified_at)
-- VALUES (605,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Wife Subscriptions',
--         5000,
--         6,
--         'Extreme Baking Plus and Drag Tiny Home Makeover Streaming',
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
--
-- -- Lifestyle
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (701,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Child Care',
--         200000,
--         7,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (702,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Pet Care',
--         3000,
--         7,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (703,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Entertainment',
--         40000,
--         7,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
--
-- -- Health
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (801,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Doctor Visits',
--         0,
--         8,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (802,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Prescription Drugs',
--         3000,
--         8,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
--
-- INSERT INTO line_items (id, budget_date, name, planned_amount_cents, category, last_modified_at)
-- VALUES (803,
--         make_timestamp(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
--         'Dentist Appointments',
--         0,
--         8,
--         make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
--                          extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
--                          extract(seconds from localtime), 'utc'))
-- ON CONFLICT DO NOTHING;
