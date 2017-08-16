package com.blackbook.botrest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */

@RestController
public class BooksController {


    @RequestMapping(value = "/api/books")
    public String getHello() {
        return "Hello";
    }
}
