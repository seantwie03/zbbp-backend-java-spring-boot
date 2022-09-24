drop table if exists line_items CASCADE;
drop table if exists categories CASCADE;
drop table if exists transactions CASCADE;
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 10000 increment by 1;

create table categories
(
    id               bigint      not null,
    budget_date      date        not null,
    name             varchar(50) not null,
    last_modified_at timestamptz not null,
    primary key (id),
    constraint name_not_empty check (
        not (name = '' or name = ' ')
        )
);
create unique index unique_name_budget_date_idx on categories (UPPER(name), budget_date);

create table line_items
(
    id               bigint      not null,
    budget_date      date        not null,
    name             varchar(50) not null,
    planned_amount   int         not null,
    category_id      bigint      not null,
    description      varchar(255),
    last_modified_at timestamptz not null,
    primary key (id),
    constraint name_not_empty check (
        not (name = '' or name = ' ')
        )
);
create unique index unique_upper_name_budget_date_idx on line_items (UPPER(name), budget_date);

create table transactions
(
    id               bigint      not null,
    date             date        not null,
    merchant         varchar(50) not null,
    amount           int         not null,
    is_income        boolean     not null default false,
    line_item_id     bigint,
    description      varchar(255),
    last_modified_at timestamptz not null,
    primary key (id),
    constraint merchant_not_empty check (
        not (merchant = '' or merchant = ' ')
        )
);

alter table line_items
    add constraint FK_category_id
        foreign key (category_id)
            references categories;

alter table transactions
    add constraint FK_line_item_id
        foreign key (line_item_id)
            references line_items;
