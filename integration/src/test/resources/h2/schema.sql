CREATE SCHEMA if not exists core;
CREATE SCHEMA if not exists hr;
CREATE SCHEMA if not exists custom;

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
create table if not exists core.SHAPE
(
    height DOUBLE,
    width  DOUBLE
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

