package com.blackbook.persistencebot.controller;

import com.blackbook.persistencebot.dao.BookstoresRepository;
import com.blackbook.persistencebot.model.Bookstore;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class BookstoreControllerTest {

    public List<Bookstore> getBookstoreTestData() {
        Bookstore matras = new Bookstore();
        matras.setId(1);
        matras.setName("matras");

        Bookstore google = new Bookstore();
        google.setId(2);
        google.setName("google");

        Bookstore taniaksiazka = new Bookstore();
        taniaksiazka.setId(3);
        taniaksiazka.setName("taniaksiazka");

        return Stream.of(matras, google, taniaksiazka).sorted(Comparator.comparing(Bookstore::getName)).collect(Collectors.toList());
    }

    @Test
    public void getBookstoresExpectTrue() {
        BookstoresRepository bookstoresRepository = mock(BookstoresRepository.class);
        BookstoreController bookstoreController = new BookstoreController(bookstoresRepository);

        List<Bookstore> expectedBookstores = getBookstoreTestData();
        when(bookstoresRepository.findAll()).thenReturn(expectedBookstores);

        List<Bookstore> actualBookstores = bookstoreController.getBookstores();

        assertEquals(expectedBookstores.size(), actualBookstores.size());
        assertEquals(expectedBookstores.get(0).getName(), actualBookstores.get(0).getName());
        assertEquals(expectedBookstores.get(1).getName(), actualBookstores.get(1).getName());
        assertEquals(expectedBookstores.get(2).getName(), actualBookstores.get(2).getName());
    }
}