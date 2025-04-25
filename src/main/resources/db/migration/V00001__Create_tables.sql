create table if not exists categories (
    id bigint not null,
    name varchar(255),
    user_id bigint,
    primary key (id));

create table if not exists transactions (
    id bigint not null,
    account varchar(255),
    customer_type smallint check (customer_type between 0 and 1),
    description varchar(255),
    inn varchar(255),
    phone varchar(255),
    recipient_bank varchar(255),
    sender_bank varchar(255),
    status smallint check (status between 0 and 6),
    sum_value numeric(16,5),
    transaction_time timestamp(6),
    transaction_type smallint check (transaction_type between 0 and 1),
    category_id bigint,
    user_id bigint,
    primary key (id));

create table if not exists users (
    id bigint not null,
    password varchar(255),
    username varchar(50),
    primary key (id));

alter table if exists categories add constraint categories_user_id foreign key (user_id) references users;

alter table if exists transactions add constraint transactions_category_id foreign key (category_id) references categories;

alter table if exists transactions add constraint transactions_user_id foreign key (user_id) references users;