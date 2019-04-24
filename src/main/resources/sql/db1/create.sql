create schema if not exists db1;

create table db1.emp
(
    id                     varchar(255)                         not null primary key,
    name              varchar(255)                         null
);