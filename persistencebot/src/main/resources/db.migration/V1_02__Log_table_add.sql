CREATE TABLE logs (
    id int not null auto_increment primary key,
    bookstore_id int,
    start_time timestamp,
    finish_time timestamp,
    result int
    FOREIGN KEY (bookstore_id) REFERENCES bookstores(id)
);