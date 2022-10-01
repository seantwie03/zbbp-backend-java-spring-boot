-- ItemTypes --
INSERT INTO item_types (type)
VALUES ('INCOME'),
       ('EXPENSE')
ON CONFLICT DO NOTHING;

-- Categories --
INSERT INTO categories (name)
VALUES ('INCOME'),
       ('SAVINGS'),
       ('INVESTMENTS'),
       ('HOUSING'),
       ('TRANSPORTATION'),
       ('FOOD'),
       ('PERSONAL'),
       ('HEALTH'),
       ('LIFESTYLE')
ON CONFLICT DO NOTHING;
