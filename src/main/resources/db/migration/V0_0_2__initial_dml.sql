-- Categories
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


INSERT INTO categories (id, name, budget_date, last_modified_at)
VALUES (9,
        'Insurance',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;


INSERT INTO categories (id, name, budget_date, last_modified_at)
VALUES (10,
        'Debt',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name, budget_date, last_modified_at)
VALUES (11,
        'Giving',
        make_date(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1),
        make_timestamptz(extract(year FROM current_date)::int, extract(month FROM current_date)::int, 1,
                         extract(hour FROM localtime)::int, extract(minutes from localtime)::int,
                         extract(seconds from localtime), 'utc'))
ON CONFLICT DO NOTHING;
