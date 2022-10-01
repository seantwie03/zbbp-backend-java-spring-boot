-- Categories
-- Previous Month
INSERT INTO categories (id, type, budget_date, name, last_modified_at)
VALUES (0,
        'INCOME',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int - 1, 1),
        'Income',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int - 1, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (10,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int - 1, 1),
        'Savings',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int - 1, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (20,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int - 1, 1),
        'Housing',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int - 1, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (30,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int - 1, 1),
        'Transportation',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int - 1, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (40,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int - 1, 1),
        'Food',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int - 1, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (50,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int - 1, 1),
        'Personal',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int - 1, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (60,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int - 1, 1),
        'Lifestyle',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int - 1, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, budget_date, name, last_modified_at)
VALUES (70,
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int - 1, 1),
        'Health',
        make_timestamptz(extract(year FROM current_date)::int, -- Year
                         extract(month FROM current_date)::int - 1, -- Month
                         1, -- Day
                         1, -- Hour
                         0, -- Minute
                         0, -- Second
                         'utc'))
ON CONFLICT DO NOTHING;
