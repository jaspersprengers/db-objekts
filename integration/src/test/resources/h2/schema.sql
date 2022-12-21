CREATE SCHEMA if not exists inventory;
CREATE SCHEMA if not exists operations;

inventory

book (id BIGINT NOT NULL primary key auto_incremet, isbn, title, author_id, published)
item(id BIGINT NOT NULL primary key auto_increment, book_id, date_acquired)
author(id BIGINT NOT NULL primary key auto_increment, name, surname, date_of_birth, date_of_death)

operations

member(id BIGINT NOT NULL  primary key auto_increment, name, surname, date_of_birth)
loan(id BIGINT NOT NULL primary key auto_increment, copy, date_loaned, date_returned)
