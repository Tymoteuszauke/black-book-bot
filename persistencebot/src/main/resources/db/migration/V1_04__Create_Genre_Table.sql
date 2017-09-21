CREATE TABLE genres (
    id int not null auto_increment primary key,
    name varchar(255)
);

ALTER TABLE books DROP COLUMN genre;
--ALTER TABLE books ADD FOREIGN KEY (genre_id) REFERENCES genres(id);

CREATE TABLE book_genre (
    book_id int,
    genre_id int,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (genre_id) REFERENCES genres(id)
);