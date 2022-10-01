-- ItemTypes --
INSERT INTO item_types (type)
VALUES ('INCOME'),
       ('EXPENSE')
ON CONFLICT DO NOTHING;
