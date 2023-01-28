-- Transactions --
-- Previous Month


--
-- Income
--
INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, description, last_modified_at)
VALUES (1100,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  1), -- Day
        'Spouse 1 Employer',
        150000,
        100,
        'Spouse 1 Paycheck 1',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, description, last_modified_at)
VALUES (1101,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  4), -- Day
        'Spouse 2 Employer',
        125000,
        101,
        'Spouse 2 Paycheck 1',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         4, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, description, last_modified_at)
VALUES (1102,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  15), -- Day
        'Spouse 1 Employer',
        150000,
        100,
        'Spouse 1 Paycheck 2',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         15, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, description, last_modified_at)
VALUES (1103,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  18), -- Day
        'Spouse 2 Employer',
        125000,
        101,
        'Spouse 2 Paycheck 2',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         18, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, description, last_modified_at)
VALUES (1104,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  25), -- Day
        'The Weasley Family',
        50000,
        102,
        'Wedding Photography',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         25, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;


--
-- Savings
--
INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, description, last_modified_at)
VALUES (1201,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  1), -- Day
        'Emergency Fund / Family Savings',
        5000,
        201,
        'Transfer to Savings Account X34889',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, description, last_modified_at)
VALUES (1202,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  1), -- Day
        'Spouse 1 Savings',
        5000,
        202,
        'Transfer to Savings Account X99398',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, last_modified_at)
VALUES (1203,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  4), -- Day
        'Spouse 2 Savings',
        5000,
        203,
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         4, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, description, last_modified_at)
VALUES (1204,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  15), -- Day
        'Emergency Fund / Family Savings',
        5000,
        201,
        'Transfer to Savings Account X34889',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         15, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, description, last_modified_at)
VALUES (1205,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  15), -- Day
        'Spouse 1 Savings',
        5000,
        202,
        'Transfer to Savings Account X99398',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         15, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, last_modified_at)
VALUES (1206,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  18), -- Day
        'Spouse 2 Savings',
        5000,
        203,
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         18, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO transactions (id, date, merchant, amount_cents, line_item_id, description, last_modified_at)
VALUES (1207,
        make_date(extract(year FROM current_date)::int, -- Year
                  extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                  25), -- Day
        'American College',
        50000,
        204,
        'Transfer to 529 Account X43984',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM date_trunc('month', current_date - interval '1 month'))::int, -- Month
                         25, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;
