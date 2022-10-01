-- Categories --
-- Current Month
INSERT INTO categories (id, type, budget_date, name, last_modified_at)
VALUES (1,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Income',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (11,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Savings',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (21,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Housing',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (31,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Transportation',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (41,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Food',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (51,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Personal',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (61,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Lifestyle',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (71,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        'Health',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;
