CREATE DATABASE IF NOT EXISTS book_lookup;
USE book_lookup;

CREATE USER IF NOT EXISTS 'bookadmin'@'localhost' IDENTIFIED BY 'pass';
GRANT ALL PRIVILEGES ON *.* TO 'bookadmin'@'localhost';

CREATE TABLE IF NOT EXISTS publisher
(
    id   INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(512)
);

CREATE TABLE IF NOT EXISTS book
(
    id           INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title        VARCHAR(512),
    year         INT,
    fk_publisher INT,
    FOREIGN KEY (fk_publisher) REFERENCES publisher (id)
);

CREATE TABLE IF NOT EXISTS author
(
    id   INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(512)        NOT NULL
);

CREATE TABLE IF NOT EXISTS author_books
(
    fk_author INT,
    fk_book   INT,
    FOREIGN KEY (fk_author) REFERENCES author (id),
    FOREIGN KEY (fk_book) REFERENCES book (id)
);