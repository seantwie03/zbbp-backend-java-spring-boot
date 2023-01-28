drop table if exists line_items CASCADE;
drop table if exists categories CASCADE;
drop table if exists transactions CASCADE;
drop sequence if exists line_items_seq;
drop sequence if exists transactions_seq;

create sequence line_items_seq start with 10000 increment by 50;
create sequence transactions_seq start with 10000 increment by 50;

create table line_items
(
  id                   bigint      not null,
  budget_date          date        not null,
  name                 varchar(50) not null,
  planned_amount_cents int         not null,
  category             varchar(20) not null,
  description          varchar(255),
  last_modified_at     timestamptz not null,
  primary key (id),
  constraint name_not_empty check (
    not (name = '' or name = ' ')
    ),
  constraint category_name_not_empty check (
    not (category = '' or category = ' ')
    )
);
comment on column line_items.budget_date is 'A budget_date represents the Month and Year of a monthly budget.
 Ideally this column would be a YYYY-MM type. Unfortunately, at the time of this writing, postgres does not have a
 type like that. Instead, this column is a Date type and the code uses an AttributeConverter to ensure the "day" is
  always set to 1.';

create unique index unique_insensitive_name_category_date_idx on line_items (upper(name), upper(category), budget_date);
comment on index unique_insensitive_name_category_date_idx is 'This constraint was originally name
 and budget_date, but I added category to it. This change allows multiple LineItems to have the same name as long as
 they are in different Categories. One example where this is useful is Insurance. You could have an Insurance
 LineItem in several categories (Transportation, Housing, Health, etc.)';

create table transactions
(
  id               bigint      not null,
  date             date        not null,
  merchant         varchar(50) not null,
  amount_cents     int         not null,
  line_item_id     bigint,
  description      varchar(255),
  last_modified_at timestamptz not null,
  primary key (id),
  constraint merchant_not_empty check (
    not (merchant = '' or merchant = ' ')
    ),
  constraint description_not_empty check (
    not (merchant = '' or merchant = ' ')
    )
);

alter table transactions
  add constraint FK_line_item_id
    foreign key (line_item_id)
      references line_items;
