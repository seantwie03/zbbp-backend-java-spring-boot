drop table if exists line_items CASCADE;
drop table if exists categories CASCADE;
drop table if exists transactions CASCADE;
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 10000 increment by 1;

create table item_types
(
    type varchar(10) default 'EXPENSE',
    primary key (type),
    constraint type_not_empty check (
        not (type = '' or type = ' ')
        )
);
create unique index unique_case_insensitive_type_idx on item_types (UPPER(type));

create table categories
(
    id               bigint      not null,
    type             varchar(10) not null default 'EXPENSE',
    budget_date      date        not null,
    name             varchar(50) not null,
    last_modified_at timestamptz not null,
    primary key (id),
    constraint name_not_empty check (
        not (name = '' or name = ' ')
        )
);
create unique index unique_case_insensitive_name_budget_date_idx on categories (UPPER(name), budget_date);

create table line_items
(
    id               bigint      not null,
    type             varchar(10) not null default 'EXPENSE',
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
create unique index unique_insensitive_name_budget_date_idx on line_items (UPPER(name), budget_date);

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
    constraint merchant_not_empty check (
        not (merchant = '' or merchant = ' ')
        )
);

alter table categories
    add constraint FK_type
        foreign key (type)
            references item_types;

alter table line_items
    add constraint FK_type
        foreign key (type)
            references item_types;

alter table transactions
    add constraint FK_type
        foreign key (type)
            references item_types;

alter table line_items
    add constraint FK_category_id
        foreign key (category_id)
            references categories;

alter table transactions
    add constraint FK_line_item_id
        foreign key (line_item_id)
            references line_items;
