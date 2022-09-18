drop table if exists categories CASCADE;
drop table if exists categories_transactions CASCADE;
drop table if exists category_groups CASCADE;
drop table if exists transactionResponseDtos CASCADE;
drop sequence if exists hibernate_sequence;


create sequence hibernate_sequence start with 10000 increment by 1;

create table category_groups
(
    id               bigint       not null,
    name             varchar(255) not null,
    budget_date      date         not null,
    last_modified_at timestamptz  not null,
    primary key (id),
    unique (name, budget_date)
);

create table categories
(
    id                bigint         not null,
    name              varchar(255)   not null,
    budget_date       date           not null,
    planned_amount    numeric(19, 2) not null,
    category_group_id bigint         not null,
    last_modified_at  timestamptz    not null,
    primary key (id),
    unique (name, budget_date)
);

create table transactions
(
    id               bigint         not null,
    description      varchar(255)   not null,
    date             date           not null,
    amount           numeric(19, 2) not null,
    is_deposit       boolean        not null default false,
    last_modified_at timestamptz    not null,
    category_id      bigint,
    primary key (id)
);

alter table categories
    add constraint FK_category_group_id
        foreign key (category_group_id)
            references category_groups;

alter table transactions
    add constraint FK_category_id
        foreign key (category_id)
            references categories;
