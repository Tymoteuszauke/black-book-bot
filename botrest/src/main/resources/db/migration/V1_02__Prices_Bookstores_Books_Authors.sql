DROP TABLE books;

CREATE TABLE bookstores (
    id int not null auto_increment primary key,
    name varchar(255),
    details varchar(300)
);

CREATE TABLE authors (
    id int not null auto_increment primary key,
    name varchar(255),
    surname varchar(500)
);

CREATE TABLE books (
    id int not null auto_increment primary key,
    title varchar(255),
    subtitle varchar(500),
    author_id int,
    genre varchar(100),
    CONSTRAINT FK_authors_books FOREIGN KEY (author_id) REFERENCES authors(id)
);

CREATE TABLE prices (
    id int not null auto_increment primary key,
    bookstore_id int,
    book_id int,
    promotion_details varchar(500),
    price decimal(16, 2),
    CONSTRAINT FK_bookstores_prices FOREIGN KEY (bookstore_id) REFERENCES bookstores(id),
    CONSTRAINT FK_books_prices FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE TABLE books_authors (
    book_id int,
    author_id int,
    CONSTRAINT FK_authors_books_authors FOREIGN KEY (author_id) REFERENCES authors(id),
    CONSTRAINT FK_books_books_authors FOREIGN KEY (book_id) REFERENCES books(id)
)