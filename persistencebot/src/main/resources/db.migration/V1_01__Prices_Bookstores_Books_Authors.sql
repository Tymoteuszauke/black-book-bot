CREATE TABLE bookstores (
    id int not null auto_increment primary key,
    name varchar(255),
    details varchar(300)
);

CREATE TABLE books (
    id int not null auto_increment primary key,
    title varchar(255),
    subtitle varchar(500),
    authors varchar(700),
    genre varchar(100),
    cover_url varchar(600),
    book_page_url varchar(600)
);

CREATE TABLE book_discounts (
    id int not null auto_increment primary key,
    bookstore_id int,
    book_id int,
    book_discount_details varchar(500),
    price decimal(16, 2),
    CONSTRAINT FK_bookstores_prices FOREIGN KEY (bookstore_id) REFERENCES bookstores(id),
    CONSTRAINT FK_books_prices FOREIGN KEY (book_id) REFERENCES books(id)
);
