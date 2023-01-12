CREATE SCHEMA if not exists library;
CREATE SCHEMA if not exists core;
CREATE SCHEMA if not exists hr;

create table if not exists library.author
(
    id   BIGINT        NOT NULL primary key auto_increment,
    name varchar(200)  NOT NULl,
    bio  varchar(1000) NULL
);

create table if not exists library.book
(
    isbn      varchar(20) primary key NOT NULL,
    title     varchar(200)            NOT NULL,
    author_id BIGINT                  NOT NULL,
    published DATE                    NOT NULL,
    foreign key (author_id) references library.author (id)
);

create table if not exists library.item
(
    id            BIGINT      NOT NULL primary key auto_increment,
    isbn          varchar(20) NOT NULL,
    date_acquired DATE        NOT NULL,
    foreign key (isbn) references library.book (isbn)
);

create table if not exists library.member
(
    id   BIGINT       NOT NULL primary key auto_increment,
    name varchar(200) NOT NULl,
    dob  date         null
);

create table if not exists library.loan
(
    item_id       BIGINT NOT NULL,
    member_id     BIGINT NOT NULL,
    date_loaned   DATE   NULL,
    date_returned DATE   NULL,
    foreign key (item_id) references library.item (id),
    foreign key (member_id) references library.member (id)
);



CREATE SEQUENCE IF NOT EXISTS core.EMPLOYEE_SEQ START WITH 10;
CREATE SEQUENCE IF NOT EXISTS core.ADDRESS_SEQ START WITH 10;
CREATE SEQUENCE IF NOT EXISTS hr.CERTIFICATE_SEQ START WITH 10;
CREATE SEQUENCE IF NOT EXISTS core.DEPARTMENT_SEQ START WITH 10;

create table IF NOT EXISTS hr.HOBBY
(
    id   varchar(10) primary key,
    name varchar(50) not null
);
create table IF NOT EXISTS core.EMPLOYEE
(
    id            BIGINT primary key,
    name          varchar(50) not null,
    salary        double      not null,
    married       boolean     null,
    date_of_birth DATE        not null,
    children      SMALLINT    null,
    hobby_id      varchar(10) null,
    foreign key (hobby_id) references hr.HOBBY (id)
);

create table IF NOT EXISTS hr.CERTIFICATE
(
    id          BIGINT primary key,
    name        varchar(50) not null,
    employee_id BIGINT      not null,
    foreign key (employee_id) references core.employee (id)
);
create table IF NOT EXISTS core.COUNTRY
(
    id   varchar(10) primary key,
    name varchar(50) not null
);

create table IF NOT EXISTS core.ADDRESS
(
    id         BIGINT primary key,
    street     varchar(50) not null,
    country_id varchar(10) not null,
    foreign key (country_id) references core.country (id)
);
create table if not exists core.EMPLOYEE_ADDRESS
(
    employee_id BIGINT      not null,
    address_id  BIGINT      not null,
    kind        varchar(10) not null,
    foreign key (employee_id) references core.employee (id),
    foreign key (address_id) references core.ADDRESS (id)
);

create table IF NOT EXISTS core.DEPARTMENT
(
    id   BIGINT primary key,
    name varchar(50) not null
);

create table if not exists core.EMPLOYEE_DEPARTMENT
(
    employee_id   BIGINT not null,
    department_id BIGINT not null,
    foreign key (employee_id) references core.employee (id),
    foreign key (department_id) references core.DEPARTMENT (id)
);


create table if not exists library.composite
(
    isbn      varchar(20)  NOT NULL,
    title     varchar(200) NOT NULL,
    published DATE         NULL,
    primary key (isbn, title)
);

create table if not exists library.composite_foreign_key
(
    isbn    varchar(20)  NOT NULL,
    title   varchar(200) NOT NULL,
    message varchar(100) null,
    primary key (isbn, title),
    foreign key (isbn, title) references library.composite (isbn, title)
);

select tc.CONSTRAINT_NAME, kcu.TABLE_SCHEMA, kcu.TABLE_NAME,kcu.COLUMN_NAME
        from INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc
        join INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu on kcu.CONSTRAINT_NAME = tc.CONSTRAINT_NAME and tc.TABLE_SCHEMA = kcu.TABLE_SCHEMA and tc.TABLE_NAME = tc.TABLE_NAME
where tc.CONSTRAINT_TYPE = 'FOREIGN KEY'


select ccu.CONSTRAINT_NAME, ccu.TABLE_SCHEMA, ccu.TABLE_NAME, ccu.COLUMN_NAME
from INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE ccu
   where ccu.CONSTRAINT_NAME = 'CONSTRAINT_23' and ccu.TABLE_NAME != 'LOAN';



select * from INFORMATION_SCHEMA.KEY_COLUMN_USAGE where CONSTRAINT_NAME = 'CONSTRAINT_2230';
select * from INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE where CONSTRAINT_NAME = 'CONSTRAINT_2230';
select * from INFORMATION_SCHEMA.TABLE_CONSTRAINTS where CONSTRAINT_NAME = 'CONSTRAINT_2230';
