create schema if not exists db2;

create table db2.emp
(
    id                     varchar(255)                         not null primary key,
    name              varchar(255)                         null
);