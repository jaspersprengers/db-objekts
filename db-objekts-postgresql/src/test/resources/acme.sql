CREATE schema if not exists core;
CREATE schema if not exists hr;

create table IF NOT EXISTS hr.HOBBY
(
    id   varchar(10) primary key ,
    name varchar(50) not null
);
create table IF NOT EXISTS core.EMPLOYEE
(
    id            BIGINT primary key GENERATED ALWAYS AS IDENTITY,
    name          varchar(50) not null,
    salary        real      not null,
    married       boolean     null,
    date_of_birth DATE        not null,
    children      SMALLINT    null,
    hobby_id      varchar(10) null,
    foreign key (hobby_id) references hr.HOBBY (id)
);

create table IF NOT EXISTS hr.CERTIFICATE
(
    id          SERIAL primary key ,
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
    id         BIGINT primary key  GENERATED ALWAYS AS IDENTITY,
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
    id   BIGINT primary key  GENERATED ALWAYS AS IDENTITY,
    name varchar(50) not null
);

create table if not exists core.EMPLOYEE_DEPARTMENT
(
    employee_id   BIGINT not null,
    department_id BIGINT not null,
    primary key(employee_id, department_id),
    foreign key (employee_id) references core.EMPLOYEE (id),
    foreign key (department_id) references core.DEPARTMENT (id)
);


create table if not exists core.composite
(
    isbn      varchar(20)  NOT NULL,
    title     varchar(200) NOT NULL,
    published DATE         NULL,
    primary key (isbn, title)
);

create table if not exists core.composite_foreign_key
(
    isbn    varchar(20)  NOT NULL,
    title   varchar(200) NOT NULL,
    message varchar(100) null,
    primary key (isbn, title),
    foreign key (isbn, title) references core.composite (isbn, title)
);

CREATE TYPE mood AS ENUM ('sad', 'ok', 'happy');

create table IF NOT EXISTS core.ALL_TYPES_NIL
(
    ID                       INTEGER primary key GENERATED ALWAYS AS IDENTITY,

    BIG_INT_COL              bigint,
    INT8_COL                 int8,
    BIGSERIAL_COL            bigserial,
    SERIAL8_COL              serial8,
    BIT4_COL                 bit(4),
    BIT_VARYING_COL          bit varying(10),
    VARBIT_COL               varbit(10),
    BOOLEAN_COL              boolean,
    BOOL_COL                 bool,
    BOX_COL                  box,
    BYTEA_COL                bytea,
    CHARACTER_COL            character(10),
    CHAR_COL                 char(10),
    CHARACTER_VARYING_COL    character varying(10),
    VARCHAR_COL              varchar(10),
    CIDR_COL                 cidr,
    CIRCLE_COL               circle,
    DATE_COL                 date,
    DOUBLE_PRECISION_COL     double precision,
    FLOAT8_COL               float8,
    INET_COL                 inet,
    INTEGER_COL              integer,
    INT_COL                  int,
    INT4_COL                 int4,
    INTERNVAL_YEAR_COL       interval year NULL,
    JSON_COL                 json,
    JSON_BINARY_COL          jsonb,
    LINE_COL                 line,
    LSEG_COL                 lseg,
    MACADDRESS_COL           macaddr,
    MACADDRESS8_COL          macaddr8,
    MONEY_COL                money,
    NUMERIC_COL              numeric,
    DECIMAL_COL              decimal,
    PATH_COL                 path,
    PG_LSN_COL               pg_lsn,
    PG_SNAPSHOT_COL          pg_snapshot,
    POINT_COL                point,
    POLYGON_COL              polygon,
    REAL_COL                 real,
    FLOAT4_COL               float4,
    SMALL_INT_COL            smallint,
    INT2_COL                 int2,
    SMALL_SERIAL_COL         smallserial,
    SERIAL2_COL              serial2,
    SERIAL_COL               serial,
    SERIAL4_COL              serial4,
    TEXT_COL                 text,
    TIME_WITHOUT_TZ_COL      time without time zone,
    TIME_WITH_TZ_COL         time with time zone,
    TIME_TZ_COL              timetz,
    TIMESTAMP_WIHTOUT_TZ_COL timestamp without time zone,
    TIMESTAMP_WITH_TZ_COL    timestamp with time zone,
    TIMESTAMP_TZ_COL         timestamptz,
    TSQUERY_COL              tsquery,
    TSVECTOR_COL             tsvector,
    TXID_SNAPSHOT_COL        txid_snapshot,
    UUID_COL                 uuid,
    XML_COL                  xml,
    MOOD_COL                 mood
);

