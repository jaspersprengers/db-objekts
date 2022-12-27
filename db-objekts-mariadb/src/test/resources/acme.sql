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
    foreign key (hobby_id) references hr.HOBBY (id)
);

create table IF NOT EXISTS hr.CERTIFICATE
(
    id          BIGINT primary key auto_increment,
    name        varchar(50) not null,
    employee_id BIGINT      not null,
    foreign key (employee_id) references core.EMPLOYEE (id)
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
    employee_id BIGINT      not null,
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

create table IF NOT EXISTS core.ALL_TYPES_NIL
(
    DECIMAL_COL          DECIMAL,
    DEC_COL              DEC,
    NUMERIC_COL          NUMERIC,
    FIXED_COL            FIXED,
    INT1_COL             INT1,
    TINYINT_COL          TINYINT(1),
    SMALLINT_COL         SMALLINT,
    INT2_COL             INT2,
    MEDIUMINT_COL        MEDIUMINT,
    INT3_COL             INT3,
    INT_COL              INT,
    INT4_COL             INT4,
    BIGINT_COL           BIGINT,
    INT8_COL             INT8,
    FLOAT_COL            FLOAT,
    DOUBLE_COL           DOUBLE,
    DOUBLE_PRECISION_COL DOUBLE PRECISION,
    BIT_COL              BIT,
    BINARY_COL           BINARY,
    BLOB_COL             BLOB,
    CHAR_COL             CHAR,
    CHAR_BYTE_COL        CHAR BYTE,
    ENUM_COL             ENUM ('YES','NO','MAYBE'),
    JSON_COL             JSON,
    TEXT_COL             TEXT,
    VARCHAR_COL          VARCHAR(50),
    SET_COL              SET ('chess','checkers','go'),
    DATE_COL             DATE,
    TIME_COL             TIME,
    DATETIME_COL         DATETIME,
    TIMESTAMP_COL        TIMESTAMP,
    YEAR_COL             YEAR
);
