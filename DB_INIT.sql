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


# Inserts below were . They were put in the DB using this application.
insert into book_lookup.author (id, name)
values (1, 'George Orwell'),
       (2, 'J. K. Rowling'),
       (3, 'Yuval Noah Harari'),
       (4, 'Benjamin Graham'),
       (5, 'Jason Zweig'),
       (6, 'Warren Buffett'),
       (7, 'Walter Isaacson'),
       (8, 'Laura Hillenbrand'),
       (9, 'Peter Thiel'),
       (10, 'Blake Masters'),
       (11, 'J.R.R. Tolkien'),
       (12, 'Kevin Kelly'),
       (13, 'Andy Hunt'),
       (14, 'Dave Thomas'),
       (15, 'George R.R. Martin'),
       (16, 'Daniel J. Levitin'),
       (17, 'A.A. Milne'),
       (18, 'Daniel Kahneman'),
       (19, 'Francis Scott Fitzgerald'),
       (20, 'Stephenie Meyer'),
       (21, 'Jonathan Clayden'),
       (22, 'Nick Greeves'),
       (23, 'Stuart Warren'),
       (24, 'Peter Wothers'),
       (25, 'Warren Sande'),
       (26, 'Carter Sande'),
       (27, 'Jude Fisher');

insert into book_lookup.publisher (id, name)
values (1, 'Secker & Warburg'),
       (3, 'Raincoast'),
       (4, 'Harvill Secker'),
       (5, 'Harper Business'),
       (6, 'Simon & Schuster'),
       (7, 'Random House'),
       (8, 'Crown Business'),
       (9, 'Houghton Mifflin'),
       (10, 'Viking'),
       (11, 'Addison-Wesley Professional'),
       (12, 'Bantam'),
       (13, 'Dutton Adult'),
       (14, 'Methuen & Co. Ltd.'),
       (15, 'Harper'),
       (16, 'Farrar, Straus and Giroux'),
       (17, 'Charles Scribner''s Sons'),
       (18, 'Little, Brown and Company'),
       (19, 'Oxford University Press'),
       (20, 'Manning Publications'),
       (21, 'Houghton Mifflin Harcourt');

insert into book_lookup.book (id, title, year, fk_publisher)
values (1, 1984, 1949, 1),
       (3, 'Harry Potter and the Philosopher''s Stone', 1998, 3),
       (4, 'Sapiens: A Brief History of Humankind', 2014, 4),
       (5, 'Harry Potter and the Order of the Phoenix', 2003, 3),
       (6, 'Harry Potter and the Deathly Hallows', 2007, 3),
       (7, 'The Intelligent Investor', 2006, 5),
       (8, 'Steve Jobs', 2011, 6),
       (9, 'Unbroken: A World War II Story of Survival, Resilience and Redemption', 2010, 7),
       (10, 'Zero to One: Notes on Startups, or How to Build the Future', 2014, 8),
       (11, 'The Hobbit, Part One', 1937, 9),
       (12, 'The Inevitable: Understanding the 12 Technological Forces That Will Shape Our Future', 2016, 10),
       (13, 'The Pragmatic Programmer: From Journeyman to Master', 1999, 11),
       (14, 'A Game of Thrones', 2005, 12),
       (15, 'This Is Your Brain on Music: The Science of a Human Obsession', 2006, 13),
       (16, 'Winnie-the-pooh', 1926, 14),
       (17, 'Homo Deus: A History of Tomorrow', 2017, 15),
       (18, 'Animal Farm', 1946, 1),
       (19, 'Thinking, Fast and Slow', 2011, 16),
       (20, 'The Great Gatsby', 1925, 17),
       (21, 'Twilight', 2005, 18),
       (22, 'Organic Chemistry', 2000, 19),
       (23, 'Hello World! Computer Programming for Kids and Other Beginners', 2008, 20),
       (24, 'The Lord of the Rings: The Fellowship of the Ring', 2001, 21);
insert into book_lookup.author_books (fk_author, fk_book)
values (2, 3),
       (2, 5),
       (2, 6),
       (4, 7),
       (5, 7),
       (6, 7),
       (7, 8),
       (8, 9),
       (9, 10),
       (10, 10),
       (12, 12),
       (13, 13),
       (14, 13),
       (15, 14),
       (16, 15),
       (17, 16),
       (3, 4),
       (3, 17),
       (1, 1),
       (1, 18),
       (18, 19),
       (19, 20),
       (20, 21),
       (21, 22),
       (22, 22),
       (23, 22),
       (24, 22),
       (25, 23),
       (26, 23),
       (11, 11),
       (11, 24),
       (27, 24);