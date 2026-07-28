create table if not exists accounts(
    account_id uuid primary key,
    user_name text not null unique,
    password_value text not null
);

create table if not exists activity_inventory(
    activity_id uuid primary key,
    user_name text not null,
    activity_name text not null,
    priority text not null
);
