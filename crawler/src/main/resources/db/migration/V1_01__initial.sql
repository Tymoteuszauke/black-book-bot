CREATE TABLE books (
    id int not null auto_increment primary key,
    author varchar(255),
    title varchar(300),
    price DECIMAL(16, 2)
);