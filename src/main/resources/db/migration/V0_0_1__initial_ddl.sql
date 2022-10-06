drop table if exists line_items CASCADE;
drop table if exists categories CASCADE;
drop table if exists transactions CASCADE;
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 10000 increment by 1;

create table line_items
(
  id               bigint      not null,
  type             varchar(10) not null default 'EXPENSE',
  budget_date      date        not null,
  name             varchar(50) not null,
  planned_amount   int         not null,
  category         varchar(20) not null,
  description      varchar(255),
  last_modified_at timestamptz not null,
  primary key (id),
  constraint type_not_empty check (
    not (type = '' or type = ' ')
    ),
  constraint name_not_empty check (
    not (name = '' or name = ' ')
    ),
  constraint category_name_not_empty check (
    not (category = '' or category = ' ')
    )
);
-- This constraint was originally name and budget_date, but I added category to it. This change allows multiple
-- LineItems to have the same name as long as they are in different Categories. One example where this is useful is
-- Insurance. You could have an Insurance LineItem in several categories (Transportation, Housing, Health, etc.)
create unique index unique_insensitive_name_category_date_idx on line_items (upper(name), upper(category), budget_date);

create table transactions
(
  id               bigint      not null,
  type             varchar(10) not null default 'EXPENSE',
  date             date        not null,
  merchant         varchar(50) not null,
  amount           int         not null,
  line_item_id     bigint,
  description      varchar(255),
  last_modified_at timestamptz not null,
  primary key (id),
  constraint type_not_empty check (
    not (type = '' or type = ' ')
    ),
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
