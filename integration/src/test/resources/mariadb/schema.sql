CREATE DATABASE if not exists core;
CREATE DATABASE if not exists hr;

create table IF NOT EXISTS hr.HOBBY
(
    id   varchar(10) primary key,
    name varchar(50) not null
);

create table IF NOT EXISTS core.EMPLOYEE
(
    id            BIGINT primary key auto_increment,
    name          varchar(50) not null,
    salary        double      not null,
    married       boolean     null,
    date_of_birth DATE        not null,
    children      SMALLINT    null,
    hobby_id      varchar(10) null,
    manager_id    BIGINT      null,
    foreign key (hobby_id) references hr.HOBBY (id),
    foreign key (manager_id) references core.EMPLOYEE (id)
);

create table IF NOT EXISTS hr.CERTIFICATE
(
    id          BIGINT primary key auto_increment,
    name        varchar(50)  not null,
    description varchar(500) null
);

create table if not exists hr.EMPLOYEE_CERTIFICATE
(
    employee_id    BIGINT not null,
    certificate_id BIGINT not null,
    date_passed    DATE   not null,
    date_expires   DATE   NULL,
    foreign key (employee_id) references core.EMPLOYEE (id),
    foreign key (certificate_id) references hr.CERTIFICATE (id)
);


create table IF NOT EXISTS core.COUNTRY
(
    id   varchar(10) primary key,
    name varchar(50) not null
);

create table IF NOT EXISTS core.ADDRESS
(
    id         BIGINT primary key auto_increment,
    street     varchar(50) not null,
    country_id varchar(10) not null,
    foreign key (country_id) references core.COUNTRY (id)
);

create table if not exists core.EMPLOYEE_ADDRESS
(
    employee_id BIGINT      not null auto_increment,
    address_id  BIGINT      not null,
    kind        varchar(10) not null,
    foreign key (employee_id) references core.EMPLOYEE (id),
    foreign key (address_id) references core.ADDRESS (id)
);

create table IF NOT EXISTS core.DEPARTMENT
(
    id   BIGINT primary key auto_increment,
    name varchar(50) not null
);

create table if not exists core.EMPLOYEE_DEPARTMENT
(
    employee_id   BIGINT not null,
    department_id BIGINT not null,
    foreign key (employee_id) references core.EMPLOYEE (id),
    foreign key (department_id) references core.DEPARTMENT (id)
);
