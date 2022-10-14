drop table if exists line_items CASCADE;
drop table if exists categories CASCADE;
drop table if exists transactions CASCADE;
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 10000 increment by 1;

create table line_items
(
  id                   bigint      not null,
  budget_date          timestamp   not null,
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
 Ideally this column would be a YYYY-MM type. Unfortunately, postgres does not have a type like that.
 Java does have a type like that called YearMonth. The code uses the YearMonth type to make the domain easier to
 reason about; however, there is a limitation in JPA that does not allow YearMonth to be converted to the postgres
 Date type. The only type that will convert correctly is java.sql.Date which is a full timestamp. This means a
 decision must be made. Either change the code to LocalDate and always set the "day" to the 1st of the month.
 Then we could use the postgres Date type. This is still not ideal because the Date type includes the day
 (YYYY-MM-DD). The other option is to use the YearMonth in the code and have to use timestamp in the database.
 I decided to go with the second option because ensuring the "day" is always set to the first of the month
 everywhere in the code is more cumbersome than ensuring the "day" is always set to 1 and the time is always set
 to 00:00:00 in the database. Perhaps in the future postgres will add a YYYY-MM type or JPA will add the
 capability to convert YearMonth to LocalDate. Until then, this column will be a timestamp (without timezone).
 The day should always be set to 1 and the time should be set to 00:00:00';

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
