-- Transactions --
-- Current Month - Today is the 17th


--
-- Income
--
INSERT INTO transactions (id, type, date, merchant, amount, line_item_id, description, last_modified_at)
VALUES (2100,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Spouse 1 Employer',
        150000,
        110,
        'Spouse 1 Paycheck 1',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, type, date, merchant, amount, line_item_id, description, last_modified_at)
VALUES (2101,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 4),
        'Spouse 2 Employer',
        125000,
        111,
        'Spouse 2 Paycheck 1',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         4, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, type, date, merchant, amount, line_item_id, description, last_modified_at)
VALUES (2102,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        'Spouse 1 Employer',
        150000,
        110,
        'Spouse 1 Paycheck 2',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         15, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;


--
-- Savings
--
INSERT INTO transactions (id, date, merchant, amount, line_item_id, description, last_modified_at)
VALUES (2201,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Emergency Fund / Family Savings',
        5000,
        211,
        'Transfer to Savings Account X34889',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, description, last_modified_at)
VALUES (2202,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Spouse 1 Savings',
        5000,
        212,
        'Transfer to Savings Account X99398',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, last_modified_at)
VALUES (2203,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 4),
        'Spouse 2 Savings',
        5000,
        213,
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         4, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, description, last_modified_at)
VALUES (2204,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        'Emergency Fund / Family Savings',
        5000,
        211,
        'Transfer to Savings Account X34889',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         15, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount, line_item_id, description, last_modified_at)
VALUES (2205,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 15),
        'Spouse 1 Savings',
        5000,
        212,
        'Transfer to Savings Account X99398',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         15, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;
