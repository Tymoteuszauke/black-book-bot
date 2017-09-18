package com.blackbook.utils.model.creationmodel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BookDiscountDataTest {

    private BookData bookData;
    private BookData otherBookData;
    private BookDiscountData discountData;
    private BookDiscountData otherDiscountData;

    @BeforeMethod
    public void before() {
        bookData = BookData.builder()
                .title("Pan Tymek")
                .subtitle("Life")
                .genre("Biografia")
                .authors("Tymke Wergiliusz")
                .publisher("ACME")
                .bookPageUrl("www.tymek.pl")
                .coverUrl("www.covers.com/tymek")
                .build();

        otherBookData = BookData.builder()
                .title("Pan Tymke")
                .subtitle("Life")
                .genre("Biografia")
                .authors("Tymke Wergiliusz")
                .publisher("ACME")
                .bookPageUrl("www.tymek.pl")
                .coverUrl("www.covers.com/tymek")
                .build();

        discountData = BookDiscountData.builder()
                .bookDiscountDetails("-50%")
                .price(24.99)
                .bookstoreId(20)
                .bookData(bookData)
                .build();

        otherDiscountData = BookDiscountData.builder()
                .bookDiscountDetails("-50%")
                .price(24.99)
                .bookstoreId(20)
                .bookData(bookData)
                .build();
    }

    @Test
    public void shouldBuildBookDiscountDataObject() throws Exception {
        // Then
        assertEquals(discountData.getBookDiscountDetails(), "-50%");
        assertEquals(discountData.getPrice(), 24.99);
        assertEquals(discountData.getBookstoreId().toString(), String.valueOf(20));
        assertEquals(discountData.getBookData(), bookData);
    }

    @Test
    public void shouldReturnTrueForEqualsObjects() {
        // Then
        assertTrue(discountData.equals(otherDiscountData));
    }

    @Test
    public void shouldReturnSameStringsForSameObjects() throws Exception {
        // Then
        assertEquals(discountData.toString(), otherDiscountData.toString());
    }

    @Test
    public void sameObjectsShouldHaveSameHashCode() {
        // Then
        assertEquals(discountData.hashCode(), otherDiscountData.hashCode());
    }

    @Test
    public void shouldReturnFalseForNotEqualsObjects() {
        // When
        otherDiscountData.setBookDiscountDetails("-25%");
        otherDiscountData.setPrice(15.49);
        otherDiscountData.setBookstoreId(10);
        otherDiscountData.setBookData(otherBookData);
        // Then
        assertFalse(discountData.equals(otherDiscountData));
    }
}