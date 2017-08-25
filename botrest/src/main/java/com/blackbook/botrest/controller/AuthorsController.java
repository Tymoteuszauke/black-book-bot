package com.blackbook.botrest.controller;

import com.blackbook.botrest.dao.AuthorsRepository;
import com.blackbook.botrest.model.Author;
import com.blackbook.botrest.model.creation_model.AuthorCreationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Transactional
@RestController
@RequestMapping(value = "/api/authors")
public class AuthorsController {

    @Autowired
    AuthorsRepository authorsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Author> getAuthors(@RequestParam(defaultValue = "") String queryParam, Pageable pageable) {
        log.info("TRANSACTION: GET /api/authors");

        return authorsRepository.findAuthorsWithTextualSearch(queryParam, pageable);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Author addAuthor(@RequestBody AuthorCreationData data) {
        log.info("TRANSACTION: POST /api/authors");

        Author author = new Author();
        author.setName(data.getName());
        author.setSurname(data.getSurname());

        authorsRepository.save(author);
        return author;
    }
}
